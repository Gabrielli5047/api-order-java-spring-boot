package br.com.desafio2.ilab.group5.services;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafio2.ilab.group5.dto.CreateOrderDTO;
import br.com.desafio2.ilab.group5.dto.SqsMessageDTO;
import br.com.desafio2.ilab.group5.model.Order;
import br.com.desafio2.ilab.group5.model.User;
import br.com.desafio2.ilab.group5.providers.AdminProvider;
import br.com.desafio2.ilab.group5.providers.SQSProvider;
import br.com.desafio2.ilab.group5.providers.UserProvider;
import br.com.desafio2.ilab.group5.repositories.OrderRepository;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository repository;
    
    
    @Autowired
    UserProvider userProvider;
    
    @Autowired
    AdminProvider adminProvider;

    @Override
    public Page<Order> list(String userId, String size, int page, String token) {
        if (size == null) {
            size = "5";
        }
        Pageable pagination = PageRequest.of(page, Integer.parseInt(size));
        if (userId != null) {
        	User user = userProvider.getUser(Integer.parseInt(userId), token);
    		if (user == null) {
    			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o usuário de id: " + userId);
    		}
        	return repository.findAllOrdersByUserId(Integer.parseInt(userId), pagination);
        }
       return repository.findAll(pagination);
    }

    @Override
    public Order create(CreateOrderDTO orderDTO, String token) {
    	
		User user = userProvider.getUser(orderDTO.getUserId(), token);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o usuário de id: " + orderDTO.getUserId());
		}
		
        Order newOrder = new Order();
        newOrder.setUserId(orderDTO.getUserId());
        newOrder.setProductsDescription(orderDTO.getProductsDescription());
        newOrder.setTotalValue(orderDTO.getTotalValue());
        newOrder.setCreatedAt(new Timestamp((new Date()).getTime()));
        newOrder.setStatus("in_progress");
        Order order = repository.save(newOrder);
        
        
        SqsMessageDTO message = new SqsMessageDTO(order.getId(), orderDTO.getTotalValue(), orderDTO.getProductsDescription(), user.getName(), user.getEmail());
        SQSProvider.sendMessage(message);   
        return order;
    }

    @Override
    public void updateStatus(Long orderId, String status) {
        Order order = repository.findById(orderId);
        if (order == null) { 
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o pedido de id: " + orderId);
        }
        order.setStatus(status);
        repository.save(order);
    }

    @Override
    public Order getOrder(Long orderId) {
    	Order order = repository.findById(orderId);
	    if (order == null) { 
	    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o pedido de id: " + orderId);
	    }
        return order;
    }
    
    @Override
    public Boolean userHasAnyOrders(int userId) { 
    	Order[] o = repository.findOrderByUserId(userId);
    	if (o.length > 0) { 
    		return true;     		
    	}
    	return false; 
    }

}
