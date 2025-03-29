package telran.dayli_farm.order.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.dayli_farm.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	@Query("""
	        SELECT o FROM Order o 
	        JOIN FETCH o.surpriseBag sb
	        JOIN FETCH sb.farmer
	        WHERE o.id = :orderId AND o.customer.id = :customerId
	    """)
	    Optional<Order> findByIdAndCustomerIdWithSurpriseBag(@Param("orderId") UUID orderId, @Param("customerId") UUID customerId);
}
