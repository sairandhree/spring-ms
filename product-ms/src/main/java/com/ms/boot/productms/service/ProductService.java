package com.ms.boot.productms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.boot.productms.model.DiscountRequest;
import com.ms.boot.productms.model.DiscountResponse;
import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductDTO;

import com.ms.boot.productms.repo.ProductRepository;



@Service
public class ProductService {
	private Logger logger;

	@Autowired
	ProductRepository repo;

	@Autowired
	RestTemplate restTemplate;


	
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Product getProductById(Integer id) {
		Optional<Product> op = repo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			return null;
	}

	public ProductDTO applyDiscountV1(Product p) {

		DiscountRequest drequest = createDiscountRequest(p);

		String url = "http://localhost:8081/caldisc";

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<DiscountRequest> dre = new HttpEntity<DiscountRequest>(drequest);

		ResponseEntity<DiscountResponse> dResponseEntity = restTemplate.exchange(url, HttpMethod.POST, dre,
				DiscountResponse.class);
		return ceateProductResponseDTO(dResponseEntity.getBody(), p);

	}

	



	private DiscountRequest createDiscountRequest(Product p) {
		return new DiscountRequest(p.getCategory(), p.getMrp());
	}

	public ProductDTO ceateProductResponseDTO(DiscountResponse discountResponse, Product p) {
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(discountResponse.getDrp());
		pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
		pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
		pdto.setId(p.getId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());

		return pdto;
	}

}
