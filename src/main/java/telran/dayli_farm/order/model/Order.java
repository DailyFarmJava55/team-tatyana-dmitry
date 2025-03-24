package telran.dayli_farm.order.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "surprise_bag_id", nullable = false)
    private SurpriseBag surpriseBag;

    @Column(nullable = false)
    private int quantity;

    @Builder.Default
    @Column(nullable = false)
    private  LocalDateTime orderTime = LocalDateTime.now();
}
