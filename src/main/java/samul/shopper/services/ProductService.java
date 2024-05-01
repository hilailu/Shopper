package samul.shopper.services;

import samul.shopper.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductDto updatedProduct);
    void deleteProduct(Long id);
    List<ProductDto> getAllProducts(Double minPrice, Double maxPrice, String category, String name, String sortBy, String sortDir);
}
