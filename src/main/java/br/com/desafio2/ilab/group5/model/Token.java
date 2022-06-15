package br.com.desafio2.ilab.group5.model;

import lombok.Data;

@Data
public class Token {
	public Token(String token) {
		this.token = token;
	}

	private String token;
}

