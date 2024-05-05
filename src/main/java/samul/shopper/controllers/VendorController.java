package samul.shopper.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.VendorDto;
import samul.shopper.services.VendorService;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/vendors")
public class VendorController {

    private VendorService vendorService;

    @PostMapping
    private ResponseEntity<VendorDto> createVendor(@RequestBody VendorDto vendorDto) {
        VendorDto savedVendor = vendorService.createVendor(vendorDto);
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<VendorDto> getVendorById(@PathVariable("id") Long id) {
        VendorDto vendorDto = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendorDto);
    }

    @GetMapping
    public ResponseEntity<List<VendorDto>> getAllVendors() {
        List<VendorDto> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @PutMapping("{id}")
    public ResponseEntity<VendorDto> updateVendor(@PathVariable("id") Long id, @RequestBody VendorDto updatedVendor) {
        VendorDto vendorDto = vendorService.updateVendor(id, updatedVendor);
        return ResponseEntity.ok(vendorDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable("id") Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
