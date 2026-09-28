package vn.iotstar.controller.graphql;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.graphql.CategoryInput;
import vn.iotstar.model.graphql.CategoryPage;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;

@Controller
public class CategoryGraphQLController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @QueryMapping
    public List<Category> allCategories() {
        return categoryService.findAll();
    }

    @QueryMapping
    public Optional<Category> categoryById(@Argument Long id) {
        return categoryService.findById(id);
    }

    @QueryMapping
    public CategoryPage categoriesPaged(
            @Argument int page,
            @Argument int size,
            @Argument String keyword) {
        if (size <= 0) size = 5;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId").descending());

        Page<Category> categoryPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            categoryPage = categoryService.findByCategoryNameContaining(keyword.trim(), pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        return CategoryPage.builder()
                .content(categoryPage.getContent())
                .totalPages(categoryPage.getTotalPages())
                .totalElements(categoryPage.getTotalElements())
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .first(categoryPage.isFirst())
                .last(categoryPage.isLast())
                .build();
    }

    @SchemaMapping(typeName = "Category", field = "products")
    public List<Product> getProducts(Category category) {
        return productService.findByCategory_CategoryId(category.getCategoryId());
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryInput input) {
        Category category = new Category();
        category.setCategoryName(input.getCategoryName());
        category.setIcon(input.getIcon());
        return categoryService.save(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput input) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (input.getCategoryName() != null && !input.getCategoryName().trim().isEmpty()) {
            category.setCategoryName(input.getCategoryName());
        }
        if (input.getIcon() != null) {
            category.setIcon(input.getIcon());
        }
        return categoryService.save(category);
    }

    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) {
            return false;
        }
        categoryService.deleteById(id);
        return true;
    }
}
