package br.com.desafio2.ilab.group5.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio2.ilab.group5.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Override
	Page<Order> findAll(Pageable pageable);
    Order findById(Long orderId);
    Page<Order> findAllOrdersByUserId(Integer userId, Pageable pageable);
    Order[] findOrderByUserId(Integer userId); 
}