package telran.goods.service;

import java.util.List;

import telran.goods.dto.AddFarmerDto;
import telran.goods.dto.AddProductDto;
import telran.goods.dto.FarmerDto;
import telran.goods.dto.ProductDto;

public interface GoodsService {
	List<ProductDto> getAllProducts();

	ProductDto getProductById(Long id);

	ProductDto createProduct(AddProductDto productDto);

	ProductDto updateProduct(Long id, AddProductDto productDto);

	void deleteProduct(Long id);

	List<FarmerDto> getAllFarmers();

	FarmerDto getFarmerById(Long id);

	FarmerDto createFarmer(AddFarmerDto farmer);

	FarmerDto updateFarmer(Long id, AddFarmerDto farmerDto);

	void deleteFarmer(Long id);
}