package br.com.desafio2.ilab.group5.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@Service
public class AdminProvider {
	@Autowired
	private WebClient webClient; 
	
	public HttpStatus validateToken(String token) { 
		try { 	
			Mono<ResponseEntity<String>> response = webClient
				.method(HttpMethod.GET)
				.uri("http://localhost:8085/validateToken")
				.accept(MediaType.APPLICATION_JSON)
				.headers(headers-> headers.setBearerAuth(token))
			    .retrieve()
			    .toEntity(String.class); 
			return response.block().getStatusCode();
			
		} catch (WebClientResponseException e) { 
			return e.getStatusCode();
		}
	}
}
