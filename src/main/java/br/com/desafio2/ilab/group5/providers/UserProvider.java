package br.com.desafio2.ilab.group5.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafio2.ilab.group5.model.User;
import lombok.Data;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@Service
@Data
public class UserProvider {
	
	@Autowired
	private WebClient webClient; 
	
	public User getUser(int id, String token) { 
		System.out.print("\n token: " + token);
		try { 	
			Mono<User> monoUser = webClient
				.method(HttpMethod.GET)
				.uri("http://localhost:8086/users/{id}", id)
				.headers(headers-> headers.setBearerAuth(token))
			    .retrieve()
			    .bodyToMono(User.class); 
			
			return monoUser.block(); 
		} catch (Exception e) { 
			if (e.getMessage().contains("404")) { 
				return null; 
			} else { 
				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível conectar ao serviço de usuários.");
			}
		}
	}
	
}
