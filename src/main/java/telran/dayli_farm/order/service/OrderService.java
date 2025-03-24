package telran.dayli_farm.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import telran.dayli_farm.customer.dao.CustomerRepository;
import telran.dayli_farm.customer.entity.Customer;
import telran.dayli_farm.order.dao.OrderRepository;
import telran.dayli_farm.order.model.Order;
import telran.dayli_farm.surprise_bag.dao.SurpriseBagRepository;
import telran.dayli_farm.surprise_bag.model.SurpriseBag;
@Service
@RequiredArgsConstructor
public class OrderService {
	private final CustomerRepository customerRepository;
    private final SurpriseBagRepository surpriseBagRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(UUID customerId, UUID surpriseBagId, int quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        SurpriseBag surpriseBag = surpriseBagRepository.findById(surpriseBagId)
               .orElseThrow(() -> new RuntimeException("Surprise Bag not found"));

        if (!surpriseBag.isAvailable()) {
            throw new RuntimeException("Surprise Bag is not available for purchase.");
        }

        if (surpriseBag.getAvailableCount() < quantity) {
            throw new RuntimeException("Not enough Surprise Bags available.");
        }

        // 🟢 Уменьшаем количество SurpriseBag
        surpriseBag.setAvailableCount(surpriseBag.getAvailableCount() - quantity);

        // 🟢 Если количество стало 0, делаем SurpriseBag недоступным
        if (surpriseBag.getAvailableCount() == 0) {
            surpriseBag.setAvailable(false);
        }

        // Сохраняем изменения в SurpriseBag
        surpriseBagRepository.save(surpriseBag);

        // Создаём новый заказ
        Order order = Order.builder()
                .customer(customer)
                .surpriseBag(surpriseBag)
                .quantity(quantity)
                .build();

        return orderRepository.save(order);
    }
}

