package br.com.desafio2.ilab.group5.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {

	private Integer id;
	private String name;
	private String cpf;
	private String email;
	private String phone;
	private LocalDate birthdate;

}
