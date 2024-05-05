package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.VendorDto;
import samul.shopper.entities.Vendor;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.mappers.VendorMapper;
import samul.shopper.repositories.VendorRepository;
import samul.shopper.services.VendorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;

    private Vendor getVendor(Long id) {
        return vendorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Vendor not found for id " + id));
    }

    @Override
    public VendorDto createVendor(VendorDto vendorDto) {
        Vendor vendor = VendorMapper.mapToVendor(vendorDto);
        Vendor savedVendor = vendorRepository.save(vendor);
        return VendorMapper.mapToVendorDto(savedVendor);
    }

    @Override
    public VendorDto getVendorById(Long id) {
        Vendor vendor = getVendor(id);
        return VendorMapper.mapToVendorDto(vendor);
    }

    @Override
    public List<VendorDto> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.stream().map((vendor) -> VendorMapper.mapToVendorDto(vendor))
                .collect(Collectors.toList());
    }

    @Override
    public VendorDto updateVendor(Long id, VendorDto updatedVendor) {
        Vendor vendor = getVendor(id);
        vendor.setVendorName(updatedVendor.getVendorName());
        vendor.setCountry(updatedVendor.getCountry());
        vendor.setPhoneNumber(updatedVendor.getPhoneNumber());
        vendor.setEmail(updatedVendor.getEmail());

        Vendor savedVendor = vendorRepository.save(vendor);

        return VendorMapper.mapToVendorDto(savedVendor);
    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}
