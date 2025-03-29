package telran.dayli_farm.order.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import telran.dayli_farm.order.dto.OrderRequestDto;
import telran.dayli_farm.order.dto.OrderResponseDto;

@Service
public interface IOrderService {
	OrderResponseDto createOrder(OrderRequestDto requestDto);

	ResponseEntity<String> cancelOrder(UUID orderId, UUID customerId);
}
