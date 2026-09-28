package vn.iotstar.controller.graphql;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import vn.iotstar.model.graphql.ProductInput;
import vn.iotstar.model.graphql.ProductPage;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;

@Controller
public class ProductGraphQLController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    // 1. Chức năng Trang Home: Hiển thị tất cả product có price từ thấp đến cao
    @QueryMapping
    public List<Product> productsSortedByPriceAsc() {
        return productService.findByOrderByUnitPriceAsc();
    }

    // 2. Chức năng Trang Home: Lấy tất cả product của 01 category
    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productService.findByCategory_CategoryId(categoryId);
    }

    // 3. Lấy thông tin chi tiết sản phẩm theo ID
    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    // 4. Tìm kiếm có phân trang trên bảng Product
    @QueryMapping
    public ProductPage productsPaged(
            @Argument int page,
            @Argument int size,
            @Argument String keyword,
            @Argument Long categoryId) {
        if (size <= 0) size = 6;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

        Page<Product> prodPage;
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = categoryId != null && categoryId > 0;

        if (hasKeyword && hasCategory) {
            prodPage = productService.findByProductNameContainingAndCategory_CategoryId(keyword.trim(), categoryId, pageable);
        } else if (hasKeyword) {
            prodPage = productService.findByProductNameContaining(keyword.trim(), pageable);
        } else if (hasCategory) {
            prodPage = productService.findByCategory_CategoryId(categoryId, pageable);
        } else {
            prodPage = productService.findAll(pageable);
        }

        return ProductPage.builder()
                .content(prodPage.getContent())
                .totalPages(prodPage.getTotalPages())
                .totalElements(prodPage.getTotalElements())
                .pageNumber(prodPage.getNumber())
                .pageSize(prodPage.getSize())
                .first(prodPage.isFirst())
                .last(prodPage.isLast())
                .build();
    }

    // Định dạng createDate thành String cho GraphQL schema
    @SchemaMapping(typeName = "Product", field = "createDate")
    public String getCreateDate(Product product) {
        if (product.getCreateDate() == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(product.getCreateDate());
    }

    // Ánh xạ kiểu status short thành Integer cho GraphQL schema
    @SchemaMapping(typeName = "Product", field = "status")
    public Integer getStatus(Product product) {
        return (int) product.getStatus();
    }

    // Ánh xạ category cho Product (tránh trường hợp lazy load hoặc null)
    @SchemaMapping(typeName = "Product", field = "category")
    public Category getCategory(Product product) {
        return product.getCategory();
    }

    // Thêm mới sản phẩm
    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        Category category = null;
        if (input.getCategoryId() != null) {
            category = categoryService.findById(input.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với id: " + input.getCategoryId()));
        }

        Product product = new Product();
        product.setProductName(input.getProductName());
        product.setQuantity(input.getQuantity());
        product.setUnitPrice(input.getUnitPrice());
        product.setImages(input.getImages());
        product.setDescription(input.getDescription());
        product.setDiscount(input.getDiscount() != null ? input.getDiscount() : 0.0);
        product.setStatus(input.getStatus() != null ? input.getStatus().shortValue() : (short) 1);
        product.setCreateDate(new Date());
        product.setCategory(category);

        return productService.save(product);
    }

    // Cập nhật sản phẩm
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput input) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Product với id: " + id));

        if (input.getProductName() != null) product.setProductName(input.getProductName());
        product.setQuantity(input.getQuantity());
        product.setUnitPrice(input.getUnitPrice());
        if (input.getImages() != null && !input.getImages().trim().isEmpty()) {
            product.setImages(input.getImages());
        }
        if (input.getDescription() != null) product.setDescription(input.getDescription());
        if (input.getDiscount() != null) product.setDiscount(input.getDiscount());
        if (input.getStatus() != null) product.setStatus(input.getStatus().shortValue());

        if (input.getCategoryId() != null) {
            Category category = categoryService.findById(input.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với id: " + input.getCategoryId()));
            product.setCategory(category);
        }

        return productService.save(product);
    }

    // Xóa sản phẩm
    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isEmpty()) {
            return false;
        }
        productService.deleteById(id);
        return true;
    }
}
