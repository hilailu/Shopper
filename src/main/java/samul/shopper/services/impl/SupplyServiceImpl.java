package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.SupplyDto;
import samul.shopper.entities.Supply;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.mappers.SupplyMapper;
import samul.shopper.repositories.SupplyRepository;
import samul.shopper.services.SupplyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    private SupplyRepository supplyRepository;

    private Supply getSupply(Long id) {
        return supplyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Supply not found for id " + id));
    }

    @Override
    public SupplyDto createSupply(SupplyDto supplyDto) {
        Supply supply = SupplyMapper.mapToSupply(supplyDto);
        Supply savedSupply = supplyRepository.save(supply);
        return SupplyMapper.mapToSupplyDto(savedSupply);
    }

    @Override
    public SupplyDto getSupplyById(Long id) {
        Supply supply = getSupply(id);
        return SupplyMapper.mapToSupplyDto(supply);
    }

    @Override
    public List<SupplyDto> getAllSupplies() {
        List<Supply> supplies = supplyRepository.findAll();
        return supplies.stream().map((supply) -> SupplyMapper.mapToSupplyDto(supply))
                .collect(Collectors.toList());
    }

    @Override
    public SupplyDto updateSupply(Long id, SupplyDto updatedSupply) {
        Supply supply = getSupply(id);
        supply.setDate(updatedSupply.getDate());
        supply.setAmount(updatedSupply.getAmount());
        supply.setPrice(updatedSupply.getPrice());
        supply.setVendor(updatedSupply.getVendor());
        supply.setProduct(updatedSupply.getProduct());

        Supply savedSupply = supplyRepository.save(supply);

        return SupplyMapper.mapToSupplyDto(savedSupply);
    }

    @Override
    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }
}
