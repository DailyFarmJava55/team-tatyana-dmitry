package telran.dayli_farm.surprise_bag.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.surprise_bag.model.Category;
import telran.dayli_farm.surprise_bag.model.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SurprisebagResponseDto {

	UUID id;

	List<Size> size;
	
	List<Category> category;

	String description;

	double price;

	int availibleCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeStart;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	LocalDateTime pickupTimeEnd;
}
