package telran.goods.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.goods.model.Category;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	 Long id;
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
