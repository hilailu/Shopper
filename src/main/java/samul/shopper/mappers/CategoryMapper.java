package samul.shopper.mappers;

import samul.shopper.dtos.CategoryDto;
import samul.shopper.entities.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryDescription()
        );
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getCategoryName(),
                categoryDto.getCategoryDescription()
        );
    }
}
