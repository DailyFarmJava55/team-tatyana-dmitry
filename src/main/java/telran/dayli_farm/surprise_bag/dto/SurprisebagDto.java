package telran.dayli_farm.surprise_bag.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import telran.dayli_farm.surprise_bag.model.Category;
import telran.dayli_farm.surprise_bag.model.Size;

@Data
@Builder
public class SurprisebagDto {

	private UUID id;
	
	private final String name = "Surprise Bag";
	
	@NotBlank(message = "Description cannot be empty")
	private String description;
	
	@NotNull(message = "Categories must be specified")
	List<Category> categories;
	
	private List<Size> size;
	
	@Min(value = 1, message = "Quantity must be at least 1")
	int availibleCount;
	
	boolean isAvailible;
	
	@NotNull(message = "Price must be specified")
	@Min(value = 0, message = "Price must be a positive value")
	private double price;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeStart;
	
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeEnd;
	
	
}
