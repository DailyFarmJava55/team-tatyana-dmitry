package telran.dayli_farm.surprise_bag.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.surprise_bag.dto.SurprisebagResponseDto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "surprise_bag")
public class SurpriseBag {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private double price;

	@Column(nullable = false)
	int availableCount;

	@Column(nullable = false)
	boolean isAvailable;

	@Column(nullable = false)
	LocalDateTime pickupTimeStart;

	@Column(nullable = false)
	LocalDateTime pickupTimeEnd;

	@ManyToOne
	@JoinColumn(name = "farmer_id", nullable = false)
	Farmer farmer;

	@ElementCollection(fetch = FetchType.LAZY, targetClass = Size.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "surprise_bag_sizes", joinColumns = @JoinColumn(name = "surprise_bag_id"))
	@Column(name = "size", nullable = false)
	private List<Size> size;

	@ElementCollection(fetch = FetchType.LAZY, targetClass = Category.class)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "surprise_bag_categories", joinColumns = @JoinColumn(name = "surprise_bag_id"))
	@Column(name = "category", nullable = false)
	private List<Category> category;

	public SurpriseBag(UUID id) {
		this.id = id;
	}

	public static SurprisebagResponseDto buildFromEntity(SurpriseBag sb) {
        return SurprisebagResponseDto.builder()
                .id(sb.id)
                .category(sb.category)
                .size(sb.size)
                .availibleCount(sb.availableCount)
                .description(sb.description)
                .price(sb.price)
                .pickupTimeStart(sb.pickupTimeStart)
                .pickupTimeEnd(sb.pickupTimeEnd)
                .build();
    }

}
