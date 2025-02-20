
package telran.goods.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.goods.dto.AddFarmerDto;
import telran.goods.dto.AddProductDto;
import telran.goods.dto.FarmerDto;
import telran.goods.dto.ProductDto;
import telran.goods.exception.FarmerNotFoundException;
import telran.goods.exception.ProductNotFoundException;
import telran.goods.model.Farmer;
import telran.goods.model.Product;
import telran.goods.repository.FarmerRepository;
import telran.goods.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

	final ModelMapper modelMapper;
	final ProductRepository productRepository;
	final FarmerRepository farmerRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> getAllProducts() {
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDto getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	@Transactional
	public ProductDto createProduct(AddProductDto productDto) {
		Farmer farmer = farmerRepository.findById(productDto.getId_farmer()).orElseThrow(
				() -> new FarmerNotFoundException("Farmer not found with id: " + productDto.getId_farmer()));
		Product product = modelMapper.map(productDto, Product.class);
		product.setFarmer(farmer);
		return modelMapper.map(productRepository.save(product), ProductDto.class);
	}

	@Override
	@Transactional
	public ProductDto updateProduct(Long id, AddProductDto productDto) {
		if (productRepository.existsById(id)) {
			Farmer farmer = farmerRepository.findById(productDto.getId_farmer()).orElseThrow(
					() -> new FarmerNotFoundException("Farmer not found with id: " + productDto.getId_farmer()));
			Product product = modelMapper.map(productDto, Product.class);
			product.setId(id);
			product.setFarmer(farmer);
			return modelMapper.map(productRepository.save(product), ProductDto.class);
		} else {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
		} else {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<FarmerDto> getAllFarmers() {
		return farmerRepository.findAll().stream().map(farmer -> modelMapper.map(farmer, FarmerDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public FarmerDto getFarmerById(Long id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new FarmerNotFoundException("Farmer not found with id: " + id));
		return modelMapper.map(farmer, FarmerDto.class);

	}

	@Override
	public FarmerDto createFarmer(AddFarmerDto farmerDto) {
		Farmer farmer = modelMapper.map(farmerDto, Farmer.class);
		farmerRepository.save(farmer);
		return modelMapper.map(farmer, FarmerDto.class);
	}

	@Override
	@Transactional
	public FarmerDto updateFarmer(Long id, AddFarmerDto farmerDto) {
		if (farmerRepository.existsById(id)) {
			Farmer farmer = modelMapper.map(farmerDto, Farmer.class);
			farmer.setId(id);
			farmerRepository.save(farmer);
			return modelMapper.map(farmer, FarmerDto.class);
		} else {
			throw new FarmerNotFoundException("Farmer not found with id: " + id);
		}
	}

	@Override
	@Transactional
	public void deleteFarmer(Long id) {
		if (farmerRepository.existsById(id)) {
			farmerRepository.deleteById(id);
		} else {
			throw new FarmerNotFoundException("Farmer not found with id: " + id);
		}
	}
}
