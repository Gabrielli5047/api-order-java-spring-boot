package br.com.desafio2.ilab.group5.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafio2.ilab.group5.providers.AdminProvider;
import lombok.Data;

@Data
@Component
public class AuthFilter implements Filter{
	@Autowired
	AdminProvider adminProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String token = httpRequest.getHeader("Authorization");

		String method = httpRequest.getMethod(); 
	
		if (method.equals("OPTIONS")) { 
			filterChain.doFilter(request, response);
			return; 
		}
	
		String path = httpRequest.getRequestURI();
	    if (path.contains("swagger") || path.contains("docs")) {
	    	filterChain.doFilter(request, response);
	    	return;
	    }
		
		if (token == null) { 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ausente");			
		}
		HttpStatus status = adminProvider.validateToken(token);
		
		if (status == HttpStatus.UNAUTHORIZED) { 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");			
		}
		
		filterChain.doFilter(request, response);	
		
	}
		
}