package telran.goods.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.goods.model.Category;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDto {
	@NotBlank
	Long id_farmer;
	@NotBlank
	String name;
	String description;
	@Min(0)
	double price;
	@Min(0)
	int quantity;
	@NotNull
	LocalDateTime expiryDate;
	@NotNull
	Category category;

}
