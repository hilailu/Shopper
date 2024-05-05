package samul.shopper.services;

import samul.shopper.dtos.SupplyDto;

import java.util.List;

public interface SupplyService {
    SupplyDto createSupply(SupplyDto supplyDto);
    SupplyDto getSupplyById(Long id);
    List<SupplyDto> getAllSupplies();
    SupplyDto updateSupply(Long id, SupplyDto updatedSupply);
    void deleteSupply(Long id);
}
