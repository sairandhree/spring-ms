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
  
  
