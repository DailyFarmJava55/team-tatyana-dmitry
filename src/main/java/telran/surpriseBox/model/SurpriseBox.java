package telran.surpriseBox.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.auth.account.model.Farmer;
import telran.auth.account.model.User;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SurpriseBox {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@NotBlank(message = "Name cannot be empty")
	private String name = "Surprise Box";

	@ElementCollection(targetClass = Category.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "surprise_box_categories", joinColumns = @JoinColumn(name = "surprise_box_id"))
	@Column(name = "category")
	private List<Category> categories;
	
	private String description;

	@ManyToOne
	@JoinColumn(name = "farmer_id", nullable = false)
	private Farmer farmer;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customer;

	@Enumerated(EnumType.STRING)
	private BoxSize boxSize;

	private double price;
	
	@Column(nullable = false)
	private int quantity;
}