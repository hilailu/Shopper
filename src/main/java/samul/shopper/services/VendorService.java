package samul.shopper.services;

import samul.shopper.dtos.VendorDto;

import java.util.List;

public interface VendorService {
    VendorDto createVendor(VendorDto vendorDto);
    VendorDto getVendorById(Long id);
    List<VendorDto> getAllVendors();
    VendorDto updateVendor(Long id, VendorDto updatedVendor);
    void deleteVendor(Long id);
}
