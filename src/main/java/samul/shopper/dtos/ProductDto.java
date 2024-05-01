package samul.shopper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samul.shopper.entities.Category;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String productName;
    private String productDescription;
    private Double price;
    private Integer quantity;
    private Set<Category> categories = new HashSet<>();
}
