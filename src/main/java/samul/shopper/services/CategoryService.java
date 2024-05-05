package samul.shopper.services;

import samul.shopper.dtos.CategoryDto;
import samul.shopper.entities.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(Long id, CategoryDto updatedCategory);
    void deleteCategory(Long id);
}
