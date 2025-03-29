package telran.dayli_farm.order.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderRequestDto {

	private UUID customerId;
	
	private UUID surpriseBagId;
	
	private int quantity;
}
