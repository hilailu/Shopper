package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.CategoryDto;
import samul.shopper.entities.Category;
import samul.shopper.exceptions.ResourceNotFoundException;
import samul.shopper.mappers.CategoryMapper;
import samul.shopper.repositories.CategoryRepository;
import samul.shopper.services.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category not found for id " + id));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = getCategory(id);
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> CategoryMapper.mapToCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto updatedCategory) {
        Category category = getCategory(id);
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setCategoryDescription(updatedCategory.getCategoryDescription());

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
