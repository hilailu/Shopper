package samul.shopper.mappers;

import samul.shopper.dtos.ProductDto;
import samul.shopper.entities.Product;

public class ProductMapper {

    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    public static Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getPrice(),
                productDto.getQuantity()
        );
    }
}
