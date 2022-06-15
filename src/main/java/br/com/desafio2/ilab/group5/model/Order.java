package br.com.desafio2.ilab.group5.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

	@NotNull(message="O id do usuário não pode ser nulo")
    @Column(name = "user_id", nullable = false)
    private Integer userId;

	@NotNull(message="O valor total não pode ser nulo")
    @Column(name = "total_value", nullable = false)
    private Double totalValue;

	@NotNull(message="A descrição dos produtos não pode ser nula")
    @Column(name = "products_description", nullable = false)
    private String productsDescription;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "status", nullable = false)
    private String status;
}