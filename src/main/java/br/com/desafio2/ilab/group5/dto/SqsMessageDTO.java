package br.com.desafio2.ilab.group5.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;

@Data
@AllArgsConstructor
public class SqsMessageDTO {
	
	@NotNull(message="O id do pedido não pode ser nulo")
    private Long orderId;
	
    @NotNull(message="O valor total do pedido não pode ser nulo")
    private Double totalValue;

	@NotNull(message="A descrição dos produtos não pode ser nula")
    private String productsDescription;

	@NotNull(message="O nome do usuário não pode ser nulo")
    private String userName;

    @Email(message = "Email inválido")
    private String userEmail;
    
    
    //Se quisermos passar todos parâmetros como atributos da mensagem: 
    public Map<String, MessageAttributeValue> buildAttributes() { 
    	Map<String, MessageAttributeValue> messageAttributes = new HashMap<String, MessageAttributeValue>();
    	messageAttributes.put("total_value", MessageAttributeValue.builder().dataType("String").stringValue(getTotalValue().toString()).build());
    	messageAttributes.put("products_description", MessageAttributeValue.builder().dataType("String").stringValue(getProductsDescription()).build());
    	messageAttributes.put("user_name", MessageAttributeValue.builder().dataType("String").stringValue(getUserName()).build());
    	messageAttributes.put("user_email", MessageAttributeValue.builder().dataType("String").stringValue(getUserEmail()).build());
    	return messageAttributes; 
    }
    
    public Map<String, MessageAttributeValue> idAttribute() { 
    	Map<String, MessageAttributeValue> messageAttribute = new HashMap<String, MessageAttributeValue>();
    	messageAttribute.put("order_id", MessageAttributeValue.builder().dataType("String").stringValue(getOrderId().toString()).build());
    	return messageAttribute; 
    }
}
