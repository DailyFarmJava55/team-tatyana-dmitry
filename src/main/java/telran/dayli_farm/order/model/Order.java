package telran.dayli_farm.order.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
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
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.farmer.entity.Farmer;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmer_id", nullable = false)
	private Farmer farmer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surprise_bag_id", nullable = false)
    private SurpriseBag surpriseBag;

    @Column(nullable = false)
    private int quantity;
    
    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

    @Column(nullable = false)
    private  LocalDateTime orderTime;
    
    @Column(nullable = false)
	private double totalPrice;
}
