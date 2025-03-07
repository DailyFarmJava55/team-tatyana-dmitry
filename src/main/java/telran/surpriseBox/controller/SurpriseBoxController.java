package telran.surpriseBox.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.surpriseBox.model.SurpriseBox;
import telran.surpriseBox.service.SurpriseBoxService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class SurpriseBoxController {
	final SurpriseBoxService surpriseBoxService;

	 @PostMapping
	    public ResponseEntity<SurpriseBox> createSurpriseBox(@RequestBody SurpriseBox surpriseBox) {
	        SurpriseBox createdSurpriseBox = surpriseBoxService.createSurpriseBox(surpriseBox);
	        return ResponseEntity.ok(createdSurpriseBox);
	    }

	    @GetMapping
	    public ResponseEntity<List<SurpriseBox>> getAllSurpriseBoxes() {
	        List<SurpriseBox> surpriseBoxes = surpriseBoxService.getAllSurpriseBoxes();
	        return ResponseEntity.ok(surpriseBoxes);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<SurpriseBox> getSurpriseBoxById(@PathVariable Long id) {
	        SurpriseBox surpriseBox = surpriseBoxService.getSurpriseBoxById(id);
	        return ResponseEntity.ok(surpriseBox);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<SurpriseBox> updateSurpriseBox(@PathVariable Long id, @RequestBody SurpriseBox surpriseBox) {
	        SurpriseBox updatedSurpriseBox = surpriseBoxService.updateSurpriseBox(id, surpriseBox);
	        return ResponseEntity.ok(updatedSurpriseBox);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteSurpriseBox(@PathVariable Long id) {
	        surpriseBoxService.deleteSurpriseBox(id);
	        return ResponseEntity.noContent().build();
	    }

}
