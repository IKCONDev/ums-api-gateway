package com.ikn.ums.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsWebConfiguration implements WebFluxConfigurer{
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowCredentials(false)
	                .allowedHeaders("*")
	                .allowedMethods("*");
	    }

	    @Bean
	    public CorsWebFilter corsWebFilter() {
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(false);
	        corsConfiguration.addAllowedHeader("Access-Control-Allow-Headers");
	        corsConfiguration.addAllowedHeader("Access-Control-Allow-Origin");
	        corsConfiguration.addAllowedHeader("*");
	        corsConfiguration.addAllowedMethod("*");
	        corsConfiguration.addAllowedOrigin("http://localhost:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.222:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.226:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.0.102:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.1.4:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.0.104:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.28:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.29:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.0.101:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.231:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.130.7:4200");
	        corsConfiguration.addAllowedOrigin("http://192.168.29.111:4200");
	        corsConfiguration.addAllowedOrigin("http://10.10.1.191:4200");
	        corsConfiguration.addAllowedOrigin("*");
	        //corsConfiguration.addAllowedOrigin("http://win-8c1huk79hrf.ikcon.administrator:8012");
	        //corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
	        corsConfiguration.addExposedHeader("token");
	        corsConfiguration.addExposedHeader("userId");
	        corsConfiguration.addExposedHeader("userRole");
	        corsConfiguration.addExposedHeader("firstName");
	        corsConfiguration.addExposedHeader("lastName");
	        corsConfiguration.addExposedHeader("email");
	        corsConfiguration.addExposedHeader("twoFactorAuth");
            corsConfiguration.addExposedHeader("jwtExpiry");
            corsConfiguration.addExposedHeader("userRoleMenuItemsPermissionMap");
            corsConfiguration.addExposedHeader("loginAttempts");
            corsConfiguration.addExposedHeader("userActive");
	        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
	        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
	        return new CorsWebFilter(corsConfigurationSource);
	    }

}
