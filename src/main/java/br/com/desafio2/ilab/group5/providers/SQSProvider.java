package br.com.desafio2.ilab.group5.providers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio2.ilab.group5.dto.SqsMessageDTO;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SQSProvider {
	
	public static void sendMessage(SqsMessageDTO sqsMessageDTO) {
		SqsClient sqsClient = AWSClientProvider.clientAuth();
			String json; 
		try {
			ObjectMapper objectMapper = new ObjectMapper(); 
			json = objectMapper.writeValueAsString(sqsMessageDTO);
			
			GetQueueUrlRequest request = GetQueueUrlRequest.builder()
					.queueName(System.getenv("SQS_QUEUE_NAME"))
					.queueOwnerAWSAccountId(System.getenv("AWS_ACCOUNT_ID")).build();
			GetQueueUrlResponse createResult = sqsClient.getQueueUrl(request);

			SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
				.queueUrl(createResult.queueUrl())
				.messageBody(json)
				.messageAttributes(sqsMessageDTO.idAttribute())
				.build();
			sqsClient.sendMessage(sendMsgRequest);
		} catch(Exception e) { 
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Não foi possível enviar a mensagem para a fila"); 	
		} finally { 
			sqsClient.close();			
		}
	}
	
}
