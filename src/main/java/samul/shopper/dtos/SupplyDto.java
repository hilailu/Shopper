package samul.shopper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samul.shopper.entities.Product;
import samul.shopper.entities.Vendor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplyDto {
    private long id;
    private Date date;
    private Double price;
    private Integer amount;
    private Vendor vendor;
    private Product product;
}
