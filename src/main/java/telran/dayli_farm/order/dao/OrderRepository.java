package telran.dayli_farm.order.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.dayli_farm.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
