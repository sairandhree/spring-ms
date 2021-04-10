package com.ms.boot.discountms;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
	

import org.springframework.web.bind.annotation.RestController;


@RestController

public class DiscountController {

	private static Logger log = LoggerFactory.getLogger(DiscountController.class);

	@Autowired
	DiscountDataMap discountDataMap;
	

	@Autowired
	Environment env; //org.springframework.core.env.Environment;
	
		
	
	@PostMapping( "/caldisc")
	public DiscountResponse calculateDiscount(@RequestBody(required = false) DiscountRequest request) {
		log.info(request.toString()  );
		log.info("\n\n running on port {}\n\n", env.getProperty("local.server.port"));
		double fixedCategoryDiscount = discountDataMap.getDiscountMap().get(request.getCategory());
		double onSpotDiscount = (new Random()).nextInt(15);
		double discountper = fixedCategoryDiscount + onSpotDiscount;
		double drp = Math.ceil(request.getMrp() - ((discountper / 100) * request.getMrp()));
		DiscountResponse response = new DiscountResponse(request.getCategory(), request.getMrp(), drp,
				fixedCategoryDiscount, onSpotDiscount);
	
		return response;
	}

	
	
}
