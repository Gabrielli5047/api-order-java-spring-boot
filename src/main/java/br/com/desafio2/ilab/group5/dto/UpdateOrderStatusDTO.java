package br.com.desafio2.ilab.group5.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDTO {

	@NotNull(message="O status do pedido não pode ser nulo")
    private String status;

}
