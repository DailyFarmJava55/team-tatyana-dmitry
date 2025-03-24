package telran.dayli_farm.order.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.dayli_farm.order.model.Order;
import telran.dayli_farm.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	 private final OrderService orderService;

	    @PostMapping("/place")
	    public ResponseEntity<Order> placeOrder(
	            @RequestParam UUID customerId,
	            @RequestParam UUID surpriseBagId,
	            @RequestParam int quantity
	    ) {
	        Order order = orderService.placeOrder(customerId, surpriseBagId, quantity);
	        return ResponseEntity.ok(order);
	    }
}
