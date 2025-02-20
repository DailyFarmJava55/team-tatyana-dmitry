package telran.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.goods.model.Product;

public interface  ProductRepository extends JpaRepository<Product, Long>{

}
