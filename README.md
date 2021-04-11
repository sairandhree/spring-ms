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
  
## make other projects clients to Eureka SErver
1) Add "Eureka Discovery client" to Productms and discount ms
2) uncomment Following properties

	spring.application.name=
	eureka.instance.instanceId= ${spring.application.name}:${random.int}
	eureka.client.registerWithEureka= true
	eureka.client.fetchRegistry = true
	eureka.client.serviceUrl.defaultZone= http://localhost:8761/eureka


3) @EnableDiscoveryClient on both project's application class
