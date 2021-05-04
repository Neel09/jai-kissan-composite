package org.jai.kissan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class JaiKissanCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaiKissanCompositeApplication.class, args);
	}

	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().build();
	}

}
