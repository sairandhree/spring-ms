package com.ms.boot.productms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ms.boot.productms.model.DiscountResponse;
import com.ms.boot.productms.model.Product;
import com.ms.boot.productms.model.ProductDTO;
import com.ms.boot.productms.service.ProductService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/product")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			return new ResponseEntity<Product>(p, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v1/{id}")
	public ResponseEntity<ProductDTO> getProductv1(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV1(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v2/{id}")
	public ResponseEntity<ProductDTO> getProductv2(@PathVariable Integer id) {
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV2(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/product/v3/{id}")
	// @Retry(name= "discountretry", fallbackMethod = "calculateDiscountFallback")
	@CircuitBreaker(name = "discountretry", fallbackMethod = "calculateDiscountFallback")
	//@RateLimiter(name = "default")
	@Bulkhead(name = "default" )
	public ResponseEntity<ProductDTO> getProductv3(@PathVariable Integer id) {
		log.info("\n\n trying \n\n");
		
		Product p = productService.getProductById(id);
		if (p != null) {
			ProductDTO pdto = productService.applyDiscountV3(p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ProductDTO> calculateDiscountFallback(Integer id, Exception e) {

		log.info("In fallback");
		Product p = productService.getProductById(id);

		if (p != null) {
			ProductDTO pdto = productService.ceateProductResponseDTO(
					new DiscountResponse(p.getCategory(), p.getMrp(), p.getMrp(), 0.0, 0.0), p);
			return new ResponseEntity<ProductDTO>(pdto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}

	}

}
