package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.ProductDto;
import samul.shopper.entities.Product;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.mappers.ProductMapper;
import samul.shopper.repositories.ProductRepository;
import samul.shopper.services.ProductService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found for id " + id));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = getProduct(id);
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map((product) -> ProductMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProducts(Double minPrice, Double maxPrice, String category, String name, String sortBy, String sortDir) {
        List<Product> products = productRepository.findAll();

        if (minPrice != null) {
            products = products.stream().filter(product -> product.getPrice().compareTo(minPrice) >= 0).collect(Collectors.toList());
        }

        if (maxPrice != null) {
            products = products.stream().filter(product -> product.getPrice().compareTo(maxPrice) <= 0).collect(Collectors.toList());
        }

        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getCategories().stream()
                            .anyMatch(cat -> cat.getCategoryName().equalsIgnoreCase(category)))
                    .collect(Collectors.toList());
        }

        if (name != null && !name.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getProductName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        Comparator<Product> comparator = Comparator.comparing(Product::getProductName);
        if (sortBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (sortBy.equalsIgnoreCase("name")) {
            comparator = Comparator.comparing(Product::getProductName);
        }
        if (sortDir.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        products.sort(comparator);

        return products.stream().map((product) -> ProductMapper.mapToProductDto(product))
                .collect(Collectors.toList());
    }


    @Override
    public ProductDto updateProduct(Long id, ProductDto updatedProduct) {
        Product product = getProduct(id);
        product.setProductName(updatedProduct.getProductName());
        product.setProductDescription(updatedProduct.getProductDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setCategories(updatedProduct.getCategories());

        Product savedProduct = productRepository.save(product);

        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
