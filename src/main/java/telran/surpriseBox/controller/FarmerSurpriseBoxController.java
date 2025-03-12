package telran.surpriseBox.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
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
import telran.surpriseBox.dto.SurpriseBoxDto;
import telran.surpriseBox.model.SurpriseBox;
import telran.surpriseBox.service.farmer.FarmerSurpriseBoxService;

@RestController
@RequestMapping("/api/farmers/{farmerId}/surprise-boxes")
@RequiredArgsConstructor
public class FarmerSurpriseBoxController {

	private final FarmerSurpriseBoxService farmerSurpriseBoxService;

	@PostMapping
	public ResponseEntity<SurpriseBox> createSurpriseBox(@PathVariable UUID farmerId,
			@RequestBody @Valid SurpriseBoxDto dto) {
		return ResponseEntity.ok(farmerSurpriseBoxService.createSurpriseBox(farmerId, dto));
	}

	@PutMapping("/{boxId}")
	public ResponseEntity<SurpriseBox> updateSurpriseBox(@PathVariable UUID farmerId, @PathVariable UUID boxId,
			@RequestBody @Valid SurpriseBoxDto dto) {
		return ResponseEntity.ok(farmerSurpriseBoxService.updateSurpriseBox(farmerId, boxId, dto));
	}

	@DeleteMapping("/{boxId}")
	public ResponseEntity<Void> deleteSurpriseBox(@PathVariable UUID farmerId, @PathVariable UUID boxId) {
		farmerSurpriseBoxService.deleteSurpriseBox(farmerId, boxId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<SurpriseBox>> getFarmerBoxes(@PathVariable UUID farmerId) {
		return ResponseEntity.ok(farmerSurpriseBoxService.getFarmerBoxes(farmerId));
	}

	@GetMapping("/orders")
	public ResponseEntity<List<SurpriseBox>> getFarmerOrders(@PathVariable UUID farmerId) {
		return ResponseEntity.ok(farmerSurpriseBoxService.getFarmerOrders(farmerId));
	}
}
