package br.com.desafio2.ilab.group5.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio2.ilab.group5.dto.CreateOrderDTO;
import br.com.desafio2.ilab.group5.dto.UpdateOrderStatusDTO;
import br.com.desafio2.ilab.group5.model.Order;
import br.com.desafio2.ilab.group5.services.OrderService;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;
    
    @GetMapping("/ping")
    @ApiOperation(value = "Teste de conexão")
    public String helloW() {
        return "pong";
    }

    @Transactional
    @PostMapping
    @ApiOperation(value = "Criação de pedido")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderDTO newOrder, @RequestHeader("Authorization") String token) {
		Order order =  orderService.create(newOrder, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping
    @ApiOperation(value = "Listagem de pedidos")
    public ResponseEntity<Page<Order>> listOrders(
    		@RequestHeader("Authorization") String token,
    		@RequestParam(name = "user_id", required=false) String userId,
    		@RequestParam(name = "size", required=false) String size,
    		@RequestParam(name = "page") int page) {
	    	Page<Order> orders = orderService.list(userId, size, page, token);
			return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

	@GetMapping("{id}")
	@ApiOperation(value = "Retornar pedido por id")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long orderId)  {
    	Order order = orderService.getOrder(orderId);
		return ResponseEntity.status(HttpStatus.OK).body(order);

    }

	@PatchMapping("{id}")
	@ApiOperation(value = "Atualizar pedido")
    public ResponseEntity<String> updateOrder(@PathVariable("id") Long orderId,  @RequestBody @Valid UpdateOrderStatusDTO orderStatusDTO)  {
    	orderService.updateStatus(orderId, orderStatusDTO.getStatus());
		return ResponseEntity.status(HttpStatus.OK).body("Pedido atualizado com sucesso");
    }
	
	@GetMapping("user/{id}")
	 public Boolean userHasAnyOrders(@PathVariable("id") String userId) {
		    return orderService.userHasAnyOrders(Integer.parseInt(userId)); 
	}
}
