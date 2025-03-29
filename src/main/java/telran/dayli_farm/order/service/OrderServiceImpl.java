package telran.dayli_farm.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.order.dao.OrderRepository;
import telran.dayli_farm.order.dto.OrderRequestDto;
import telran.dayli_farm.order.dto.OrderResponseDto;
import telran.dayli_farm.order.model.Order;
import telran.dayli_farm.order.model.OrderStatus;
import telran.dayli_farm.surprise_bag.dao.SurpriseBagRepository;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
	private final CustomerRepository customerRepository;
	private final SurpriseBagRepository surpriseBagRepository;
	private final OrderRepository orderRepository;

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto) {
	    Customer customer = customerRepository.findById(requestDto.getCustomerId())
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    SurpriseBag surpriseBag = surpriseBagRepository.findById(requestDto.getSurpriseBagId())
	            .orElseThrow(() -> new RuntimeException("Surprise Bag not found"));

	    int quantity = requestDto.getQuantity();

	    if (!surpriseBag.isAvailable() || surpriseBag.getAvailableCount() < quantity) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough Surprise Bags available.");
	    }

	    surpriseBag.setAvailableCount(surpriseBag.getAvailableCount() - quantity);
	    if (surpriseBag.getAvailableCount() == 0) {
	        surpriseBag.setAvailable(false);
	    }

	    surpriseBagRepository.saveAndFlush(surpriseBag);

	    Order order = Order.builder()
	            .customer(customer)
	            .farmer(surpriseBag.getFarmer())
	            .surpriseBag(surpriseBag)
	            .quantity(quantity)
	            .status(OrderStatus.WAITING_FOR_PAYMENT)
	            .orderTime(LocalDateTime.now())
	            .totalPrice(surpriseBag.getPrice() * quantity)
	            .build();

	    orderRepository.saveAndFlush(order);

	    return new OrderResponseDto(order.getId(), order.getTotalPrice(), "Order Created!", order.getStatus());
	}

	@Override
	@Transactional
	public ResponseEntity<String> cancelOrder(UUID orderId, UUID customerId) {
	    Order order = orderRepository.findByIdAndCustomerIdWithSurpriseBag(orderId, customerId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or you are not the owner"));

	    SurpriseBag surpriseBag = order.getSurpriseBag();

	    if (surpriseBag == null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Surprise Bag not found for this order");
	    }
	    
	    surpriseBag.setAvailableCount(surpriseBag.getAvailableCount() + order.getQuantity());
	    surpriseBag.setAvailable(true);
	    
	    order.setStatus(OrderStatus.CANCELED);
	    
	    orderRepository.save(order);
	    surpriseBagRepository.save(surpriseBag);
	    
	    log.info("OrderService : Cancel order - changing status for order to CANCEL");
	    
	    return ResponseEntity.ok("Order successfully canceled and Surprise Bag availability restored");
	}
}
