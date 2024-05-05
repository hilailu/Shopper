package samul.shopper.mappers;

import samul.shopper.dtos.VendorDto;
import samul.shopper.entities.Vendor;

public class VendorMapper {
    public static VendorDto mapToVendorDto(Vendor vendor) {
        return new VendorDto(
                vendor.getId(),
                vendor.getVendorName(),
                vendor.getPhoneNumber(),
                vendor.getEmail(),
                vendor.getCountry()
        );
    }

    public static Vendor mapToVendor(VendorDto vendorDto) {
        return new Vendor(
                vendorDto.getId(),
                vendorDto.getVendorName(),
                vendorDto.getPhoneNumber(),
                vendorDto.getEmail(),
                vendorDto.getCountry()
        );
    }
}
