package samul.shopper.mappers;

import samul.shopper.dtos.VendorDto;
import samul.shopper.entities.Vendor;

public class VendorMapper {
    public static VendorDto mapToVendorDto(Vendor vendor) {
        return new VendorDto(
                vendor.getId(),
                vendor.getVendorName(),
                vendor.getEmail(),
                vendor.getPhoneNumber(),
                vendor.getCountry()
        );
    }

    public static Vendor mapToVendor(VendorDto vendorDto) {
        return new Vendor(
                vendorDto.getId(),
                vendorDto.getVendorName(),
                vendorDto.getEmail(),
                vendorDto.getPhoneNumber(),
                vendorDto.getCountry()
        );
    }
}
