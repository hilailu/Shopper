package samul.shopper.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.SupplyDto;
import samul.shopper.services.SupplyService;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/supplies")
public class SupplyController {

    private SupplyService supplyService;

    @PostMapping
    private ResponseEntity<SupplyDto> createSupply(@RequestBody SupplyDto supplyDto) {
        SupplyDto savedSupply = supplyService.createSupply(supplyDto);
        return new ResponseEntity<>(savedSupply, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplyDto> getSupplyById(@PathVariable("id") Long id) {
        SupplyDto supplyDto = supplyService.getSupplyById(id);
        return ResponseEntity.ok(supplyDto);
    }

    @GetMapping
    public ResponseEntity<List<SupplyDto>> getAllSupplies() {
        List<SupplyDto> supplies = supplyService.getAllSupplies();
        return ResponseEntity.ok(supplies);
    }

    @PutMapping("{id}")
    public ResponseEntity<SupplyDto> updateSupply(@PathVariable("id") Long id, @RequestBody SupplyDto updatedSupply) {
        SupplyDto supplyDto = supplyService.updateSupply(id, updatedSupply);
        return ResponseEntity.ok(supplyDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSupply(@PathVariable("id") Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.ok("Supply deleted successfully");
    }
}
