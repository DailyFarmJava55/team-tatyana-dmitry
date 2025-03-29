package telran.dayli_farm.order.controller;
 
import static telran.dayli_farm.api.ApiConstants.CANCEL_ORDER;
import static telran.dayli_farm.api.ApiConstants.CREATE_ORDER;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.dayli_farm.order.dto.OrderRequestDto;
import telran.dayli_farm.order.dto.OrderResponseDto;
import telran.dayli_farm.order.service.IOrderService;
import telran.dayli_farm.security.CustomUserDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

	 private final IOrderService orderServiсe;
	 
	    @PostMapping(CREATE_ORDER)
	    @PreAuthorize("hasRole('CUSTOMER')")
	   public ResponseEntity<OrderResponseDto>createOrder(@RequestBody OrderRequestDto requestDto){
	    	OrderResponseDto response = orderServiсe.createOrder(requestDto);
	        return ResponseEntity.ok(response);
	    }
	    
	    @DeleteMapping(CANCEL_ORDER + "/{orderId}")
	    @PreAuthorize("hasRole('CUSTOMER')")
	    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId,
	                                              @AuthenticationPrincipal CustomUserDetails user) {
	        return orderServiсe.cancelOrder(orderId, user.getId());
	    }
	    
	    
	    
}
