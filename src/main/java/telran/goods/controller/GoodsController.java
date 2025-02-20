package telran.goods.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.goods.dto.AddFarmerDto;
import telran.goods.dto.AddProductDto;
import telran.goods.dto.FarmerDto;
import telran.goods.dto.ProductDto;
import telran.goods.service.GoodsService;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

	final GoodsService goodsService;

	@GetMapping("/products")
	public List<ProductDto> getAllProducts() {
		return goodsService.getAllProducts();
	}

	@GetMapping("/products/{id}")
	public ProductDto getProductById(@PathVariable Long id) {
		return goodsService.getProductById(id);
	}

	@PostMapping("/products/add")
	public ProductDto createProduct(@Valid @RequestBody AddProductDto product) {
		return goodsService.createProduct(product);
	}

	@PutMapping("/products/upd/{id}")
	public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody AddProductDto product) {
		return goodsService.updateProduct(id, product);
	}

	@DeleteMapping("/products/del/{id}")
	public void deleteProduct(@PathVariable Long id) {
		goodsService.deleteProduct(id);
	}

	@GetMapping("/farmers")
	public List<FarmerDto> getAllFarmers() {
		return goodsService.getAllFarmers();
	}

	@GetMapping("/farmers/{id}")
	public FarmerDto getFarmerById(@PathVariable Long id) {
		return goodsService.getFarmerById(id);
	}

	@PostMapping("/farmers/add")
	public FarmerDto createFarmer(@Valid @RequestBody AddFarmerDto farmer) {
		return goodsService.createFarmer(farmer);
	}

	@PutMapping("/farmers/upd/{id}")
	public FarmerDto updateFarmer(@PathVariable Long id, @Valid @RequestBody AddFarmerDto farmer) {
		return goodsService.updateFarmer(id, farmer);
	}

	@DeleteMapping("/farmers/del/{id}")
	public void deleteFarmer(@PathVariable Long id) {
		goodsService.deleteFarmer(id);
	}
}