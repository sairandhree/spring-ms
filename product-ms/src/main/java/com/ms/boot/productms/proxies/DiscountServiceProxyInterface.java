package com.ms.boot.productms.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ms.boot.productms.model.DiscountRequest;
import com.ms.boot.productms.model.DiscountResponse;

import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "DISCOUNTMS")

public interface DiscountServiceProxyInterface {

	@RequestMapping(value = "/caldisc", method = RequestMethod.POST) //localhost:51165/caldisc
	public DiscountResponse calculateDiscount(DiscountRequest request);
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET) //localhost:51165/test
	public String test(String str);
	
}