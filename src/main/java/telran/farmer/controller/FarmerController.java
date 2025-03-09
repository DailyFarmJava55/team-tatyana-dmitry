package telran.farmer.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.auth.account.model.Farmer;
import telran.farmer.dto.FarmerUpdateRequest;
import telran.farmer.service.FarmerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/farmers")
public class FarmerController {

	private final FarmerService farmerService;

	@GetMapping
	public ResponseEntity<List<Farmer>> getAllFarmers() {
		return ResponseEntity.ok(farmerService.getAllFarmers());
	}
	
	@GetMapping("/{farmName}")
	public ResponseEntity<Farmer> getFarmerByFarmName(@PathVariable String farmName) {
        return ResponseEntity.ok(farmerService.getFarmerByFarmName(farmName));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Farmer> getFarmerById(@PathVariable UUID id) {
		return ResponseEntity.ok(farmerService.getFarmerById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<Farmer> updateFarmer(@PathVariable UUID id, @RequestBody FarmerUpdateRequest request) {
		return ResponseEntity.ok(farmerService.updateFarmer(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteFarmer(@PathVariable UUID id) {
		farmerService.deleteFarmer(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/profile")
	public ResponseEntity<Farmer> getCurrentFarmer(Principal principal) {
		return ResponseEntity.ok(farmerService.getFarmerByEmail(principal.getName()));
    }
}
