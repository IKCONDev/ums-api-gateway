package com.ikn.ums.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
/*
 *This filter executes before a particular route or path or 
 *HTTP request is performed to check the authorization
 *In order to execute this filter first before any request it should
 *extend from AbstractGatewayFilterFactory
*/
@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{

    @Autowired
    private Environment env;
    
    public AuthorizationHeaderFilter() {
	super(Config.class);
    }
    
    public static class Config{
	// put some config properties here for the filter if needed
    }

    //we can get HTTP request obeject / details from exchange object and from 
    // request object we can get HTTP authorization header
    
    //chain object is used to deletgate the flow to next filter in chain
    @Override
    public GatewayFilter apply(Config config) {
	log.info("AuthorizationHeaderFilter.apply() entered with args - config object");
	return (exchange, chain)->{
	    ServerHttpRequest request =  exchange.getRequest();
	    log.info("AuthorizationHeaderFilter.apply() : Request object obtained");
	    if(!request.getHeaders().containsKey("Authorization")) {
	    	log.info("AuthorizationHeaderFilter.apply() : Header doesn't contains Authorization token");
		return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);	
	    }
	   String authorizationHeader =  request.getHeaders().get("Authorization").get(0);
	   log.info("AuthorizationHeaderFilter.apply() :  Authorization header retrieved from request object");
	   String jwtToken = authorizationHeader.replace("Bearer ","");
	   if(!isJwtValid(jwtToken)) {
		   log.info("AuthorizationHeaderFilter.apply() :  Authorization header retrieved from request object is not a valid one - UNAUTHORIZED");
	       return onError(exchange, "Not a valid JWT Token", HttpStatus.UNAUTHORIZED);	
	   }
//	   if(isJwtExpired(jwtToken)) {
//		   log.info("AuthorizationHeaderFilter.apply() :  Authorization header retrieved from request object is not a valid one - UNAUTHORIZED");
//	       return onError(exchange, "Not a valid JWT Token", HttpStatus.UNAUTHORIZED);	
//	   }
	   return chain.filter(exchange);
	    };
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
	ServerHttpResponse response = exchange.getResponse();
	response.setStatusCode(httpStatus);
	return response.setComplete();
    }
    
    /**
     * 
     * @param jwtToken
     * @return
     */
    /**
    private boolean isJwtExpired(String jwtToken) {
    	Date expiresAt = null;
    	if(jwtToken != null && jwtToken != "") {
    		expiresAt = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
		 .parseClaimsJwt(jwtToken)
		 .getBody()
		 .getExpiration();
    	}
    	System.out.println(expiresAt);
    	return expiresAt.before(new Date());
    }
    */
    
    private boolean isJwtValid(String jwtToken) {
    log.info("AuthorizationHeaderFilter.isJwtValid() entered with args - jwtToken : "+jwtToken);
	boolean returnValue = true;
	//get subject
	String userId = null;
	//String role = null;
	//Date expiresAt = null;
	try {
		 log.info("AuthorizationHeaderFilter.isJwtValid() is under execution...");
	     userId= Jwts.parser()
			.setSigningKey(env.getProperty("token.secret"))
			.parseClaimsJws(jwtToken)
			.getBody()
			.getSubject();
	     if(userId == null || userId.isEmpty()){
	    	 log.info("AuthorizationHeaderFilter.isJwtValid() invalid jwt token");
	 	    returnValue = false;
	 	} 
	     log.info("AuthorizationHeaderFilter.isJwtValid() valid jwt token");
	     log.info("AuthorizationHeaderFilter.isJwtValid() executed succesfully");
	     return returnValue;
	}//try
	catch (Exception e) {
		log.info("AuthorizationHeaderFilter.isJwtValid() : Exception occured while validating jwt token "+e.getMessage());
	   returnValue = false;
	   return returnValue;
	   //response.setHeader("error",e.getMessage())
//	   Map<String, String> tokenData = new HashMap<String, String>();
//		tokenData.put("token", jwtToken);
//		response.setContentType("application/json");
//		new ObjectMapper().writeValue(response.getOutputStream(), tokenData);
	} 
   }
    
}
