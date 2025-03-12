package telran.surpriseBox.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.surpriseBox.model.BoxSize;
import telran.surpriseBox.model.Category;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurpriseBoxDto {

	@NotBlank(message = "Name cannot be empty")
	private String name = "Surprise Box";

	@NotBlank(message = "Description cannot be empty")
	private String description;

	@NotNull(message = "Price must be specified")
	@Min(value = 0, message = "Price must be a positive value")
	private double price;

	@NotNull(message = "Box size must be specified")
	private BoxSize boxSize;

	@NotNull(message = "Categories must be specified")
	List<Category> categories;

	@Min(value = 1, message = "Quantity must be at least 1")
	int quantity;
}
