package samul.shopper.mappers;

import samul.shopper.dtos.SupplyDto;
import samul.shopper.entities.Supply;

public class SupplyMapper {

    public static SupplyDto mapToSupplyDto(Supply supply) {
        return new SupplyDto(
                supply.getId(),
                supply.getDate(),
                supply.getPrice(),
                supply.getAmount(),
                supply.getVendor(),
                supply.getProduct()
        );
    }

    public static Supply mapToSupply(SupplyDto supplyDto) {
        return new Supply(
                supplyDto.getId(),
                supplyDto.getDate(),
                supplyDto.getPrice(),
                supplyDto.getAmount(),
                supplyDto.getVendor(),
                supplyDto.getProduct()
        );
    }
}
