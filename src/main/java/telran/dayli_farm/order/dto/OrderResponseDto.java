package telran.dayli_farm.order.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import telran.dayli_farm.order.model.OrderStatus;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
	
	private UUID orderId;

	private double totalPrice;

	private String message;
	
	private OrderStatus status;
}
