# spring-ms
## create Eureka Server 
Create Eureka Server
1) Create new spring starter project 
	Name: EurekaServer
	Dependencies: eureka server

2) add @EnableEurekaServer to EurekaServerApplication class

3) add following properties to application.properties
	server.port=8761
	eureka.client.register-with-eureka=false
	eureka.client.fetch-registry=false
  
## Make other projects clients to Eureka SErver
1) Add dependancy "Eureka Discovery client" to Productms and discount ms
2) uncomment Following properties

	spring.application.name=
	eureka.instance.instanceId= ${spring.application.name}:${random.int}
	eureka.client.registerWithEureka= true
	eureka.client.fetchRegistry = true
	eureka.client.serviceUrl.defaultZone= http://localhost:8761/eureka


3) @EnableDiscoveryClient on both project's application class

## Add Actuator to ProductMS
1) add dependancy for actuator
2) expose all the metrics endpoints by adding following property 
	management.endpoints.web.exposure.include=*
	
## Use Feign client
1)  add openfeign dependancy to ProductMs

2) Add @EnableFeignClient to ProductMsApplication

3) Create DiscountMsProxyInterface 
	`
		@FeignClient(name = "DISCOUNTMS")
		public interface DiscountServiceProxyInterface {

		@RequestMapping(value = "/caldisc", method = RequestMethod.POST) 
		public DiscountResponse calculateDiscount(DiscountRequest request);
	
	
		@RequestMapping(value = "/test", method = RequestMethod.GET) 
		public String test();
	
	}
	`
4) Call FeignProxy from ProductService
	
	`
	
		@Autowired
		DiscountServiceProxyInterface discountProxy;

		public ProductDTO applyDiscountV3(Product p) {
			DiscountRequest drequest = createDiscountRequest(p);

			return ceateProductResponseDTO(discountProxy.calculateDiscount(drequest), p);
		}
	`
	
