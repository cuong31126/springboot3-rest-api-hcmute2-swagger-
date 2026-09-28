/**
 * TÊN FILE: ProductApiController.java
 * CHỨC NĂNG: Cung cấp RESTful API quản lý Sản phẩm (Products).
 */
package vn.iotstar.controller.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IStorageService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    /**
     * Lấy toàn bộ danh sách sản phẩm
     * GET /api/product
     */
    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(
                new Response(true, "Thành công", productService.findAll()),
                HttpStatus.OK
        );
    }

    /**
     * Thêm sản phẩm mới kèm hình ảnh và liên kết danh mục
     * POST /api/product/addProduct
     */
    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(
            @Validated @RequestParam("productName") String productName,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @Validated @RequestParam("unitPrice") Double unitPrice,
            @Validated @RequestParam(value = "discount", defaultValue = "0") Double discount,
            @RequestParam(value = "description", defaultValue = "") String description,
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("quantity") Integer quantity,
            @Validated @RequestParam("status") Short status) {

        // Kiểm tra tên sản phẩm trùng lặp
        Optional<Product> optProduct = productService.findByProductName(productName);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Sản phẩm này đã tồn tại trong hệ thống", optProduct.get()),
                    HttpStatus.BAD_REQUEST
            );
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setStatus(status);
        product.setCreateDate(new Date());

        // Gán Category cho Product
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isPresent()) {
            product.setCategory(optCategory.get());
        }

        // Lưu file ảnh nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setImages(storageService.getSorageFilename(imageFile, uuString));
            storageService.store(imageFile, product.getImages());
        }

        productService.save(product);

        return new ResponseEntity<Response>(
                new Response(true, "Thêm sản phẩm thành công", product),
                HttpStatus.OK
        );
    }

    /**
     * Lấy chi tiết sản phẩm theo ID
     * POST /api/product/getProduct?id=...
     */
    @PostMapping(path = "/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam("id") Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(true, "Thành công", optProduct.get()),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<Response>(
                new Response(false, "Không tìm thấy sản phẩm", null),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * Cập nhật sản phẩm
     * PUT hoặc POST /api/product/updateProduct
     */
    @RequestMapping(path = "/updateProduct", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<?> updateProduct(
            @Validated @RequestParam("productId") Long productId,
            @Validated @RequestParam("productName") String productName,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @Validated @RequestParam("unitPrice") Double unitPrice,
            @Validated @RequestParam(value = "discount", defaultValue = "0") Double discount,
            @RequestParam(value = "description", defaultValue = "") String description,
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("quantity") Integer quantity,
            @Validated @RequestParam("status") Short status) {

        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy sản phẩm để cập nhật", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        Product product = optProduct.get();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setStatus(status);

        // Cập nhật danh mục
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isPresent()) {
            product.setCategory(optCategory.get());
        }

        // Cập nhật ảnh mới nếu có
        if (imageFile != null && !imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            product.setImages(storageService.getSorageFilename(imageFile, uuString));
            storageService.store(imageFile, product.getImages());
        }

        productService.save(product);

        return new ResponseEntity<Response>(
                new Response(true, "Cập nhật sản phẩm thành công", product),
                HttpStatus.OK
        );
    }

    /**
     * Xóa sản phẩm
     * DELETE /api/product/deleteProduct?productId=...
     */
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy sản phẩm để xóa", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        productService.deleteById(productId);

        return new ResponseEntity<Response>(
                new Response(true, "Xóa sản phẩm thành công", optProduct.get()),
                HttpStatus.OK
        );
    }
}
