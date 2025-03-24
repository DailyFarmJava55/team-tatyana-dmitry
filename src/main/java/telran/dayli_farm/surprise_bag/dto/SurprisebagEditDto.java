package telran.dayli_farm.surprise_bag.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import telran.dayli_farm.surprise_bag.model.Category;
import telran.dayli_farm.surprise_bag.model.Size;

@Data
@Builder
public class SurprisebagEditDto {
	private String description;

	List<Category> categories;

	private List<Size> size;

	int availableCount;

	boolean isAvailable;

	@Min(value = 0, message = "Price must be a positive value")
	private double price;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeStart;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeEnd;
}
