package com.dd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

//@EnablePrometheusEndpoint
@SpringBootApplication
//@EnableSpringBootMetricsCollector
@CrossOrigin
public class UserMngmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMngmtApplication.class, args);
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	
	/*
	@Bean
	public SpringBootMetricsCollector springBootMetricsCollector(Collection<PublicMetrics> publicMetrics) {
	    SpringBootMetricsCollector springBootMetricsCollector = new SpringBootMetricsCollector(publicMetrics);
	    springBootMetricsCollector.register();
	    return springBootMetricsCollector;
	}*

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
	    DefaultExports.initialize();
	    return new ServletRegistrationBean(new MetricsServlet(), "/prometheus");
	}
	*/
}