package telran.dailyFarm.accounting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.dailyFarm.accounting.dto.farmer.FarmerDto;
import telran.dailyFarm.accounting.service.FarmerService;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

	private final FarmerService farmerService;
	
	    @GetMapping
	    public ResponseEntity<Iterable<FarmerDto>> getAllFarmers() {
	        return ResponseEntity.ok(farmerService.getAllFarmers());
	    }
	    
	    @GetMapping("/city/{city}")
	    public ResponseEntity<Iterable<FarmerDto>> getFarmersByCity(@PathVariable String city) {
	        return ResponseEntity.ok(farmerService.findFarmersByCity(city));
	    }

	    @GetMapping("/nearby")
	    public ResponseEntity<Iterable<FarmerDto>> getNearbyFarmers(
	            @RequestParam double latitude,
	            @RequestParam double longitude,
	            @RequestParam double radius) {
	        return ResponseEntity.ok(farmerService.findNearbyFarmers(latitude, longitude, radius));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<FarmerDto> getFarmerById(@PathVariable Long id) {
	        return ResponseEntity.ok(farmerService.getFarmerById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<FarmerDto> updateFarmer(@PathVariable Long id, @RequestBody FarmerDto dto) {
	        return ResponseEntity.ok(farmerService.updateFarmer(id, dto));
	    }

}
