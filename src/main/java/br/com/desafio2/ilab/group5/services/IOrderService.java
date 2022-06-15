package br.com.desafio2.ilab.group5.services;

import org.springframework.data.domain.Page;

import br.com.desafio2.ilab.group5.dto.CreateOrderDTO;
import br.com.desafio2.ilab.group5.model.Order;

public interface IOrderService {

    Page<Order> list(String userId, String size, int page, String token);

    Order create(CreateOrderDTO orderDTO, String token);

    void updateStatus(Long orderId, String status);

    Order getOrder(Long orderId);
    
    Boolean userHasAnyOrders(int userId); 
}
