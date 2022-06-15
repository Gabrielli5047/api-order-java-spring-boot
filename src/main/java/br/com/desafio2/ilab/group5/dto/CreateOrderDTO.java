package br.com.desafio2.ilab.group5.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateOrderDTO {
	@Min(1)
	@NotNull(message="O id do usuário não pode ser nulo")
    private Integer userId;

	@DecimalMin("0.0")
	@NotNull(message="O valor total do pedido não pode ser nulo")
    private Double totalValue;

	@Size(min=3, max=250, message="A descrição dos produtos deve ser maior que 2 caracteres e menor que 500")
    private String productsDescription;
}
