/**
 * TÊN FILE: CategoryAPIController.java
 * CHỨC NĂNG: Cung cấp các RESTful API phục vụ thao tác CRUD cho Category (Danh mục).
 * DÀNH CHO NGƯỜI MỚI:
 * - @RestController: Đánh dấu class này là Controller chuyên trả về dữ liệu thô (JSON),
 *   thay vì trả về trang giao diện HTML thông thường.
 * - @RequestMapping("/api/category"): Mọi URL gọi vào các hàm trong class này
 *   đều sẽ bắt đầu bằng đường dẫn tiền tố "/api/category".
 * - Tầng này nhận dữ liệu từ Client (Postman / Ajax Form), gọi Service xử lý,
 *   rồi bọc kết quả vào đối tượng Response (status, message, body) để gửi về cho Client.
 */
package vn.iotstar.controller.api;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    // Spring tự động tiêm (inject) đối tượng CategoryServiceImpl vào biến này
    @Autowired
    private ICategoryService categoryService;

    // Spring tự động tiêm đối tượng FileSystemStorageServiceImpl để xử lý upload ảnh
    @Autowired
    private IStorageService storageService;

    /**
     * API 1: Lấy toàn bộ danh sách danh mục
     * Phương thức: GET
     * URL: http://localhost:8080/api/category
     */
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<Response>(
                new Response(true, "Thành công", categoryService.findAll()), 
                HttpStatus.OK
        );
    }

    /**
     * API 2: Lấy thông tin chi tiết 1 danh mục theo ID
     * Phương thức: POST
     * URL: http://localhost:8080/api/category/getCategory?id=1
     */
    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(true, "Thành công", category.get()), 
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<Response>(
                    new Response(false, "Thất bại, không tìm thấy danh mục", null), 
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * API 3: Thêm mới danh mục (kèm upload file icon)
     * Phương thức: POST (dạng form-data)
     * URL: http://localhost:8080/api/category/addCategory
     */
    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        // 1. Kiểm tra xem tên danh mục đã có trong CSDL chưa
        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Category đã tồn tại trong hệ thống", null), 
                    HttpStatus.BAD_REQUEST
            );
        }

        Category category = new Category();
        category.setCategoryName(categoryName);

        // 2. Kiểm tra nếu có đính kèm file icon thì lưu file
        if (icon != null && !icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            // Đặt tên file mới theo chuẩn: p<uuid>.<extension>
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            // Thực hiện ghi file vật lý vào thư mục "uploads"
            storageService.store(icon, category.getIcon());
        }

        // 3. Lưu vào Database
        categoryService.save(category);

        return new ResponseEntity<Response>(
                new Response(true, "Thêm Thành công", category), 
                HttpStatus.OK
        );
    }

    /**
     * API 4: Cập nhật thông tin danh mục (có thể upload ảnh mới hoặc giữ ảnh cũ)
     * Phương thức: PUT (dạng form-data)
     * URL: http://localhost:8080/api/category/updateCategory
     */
    @PutMapping(path = "/updateCategory")
    public ResponseEntity<?> updateCategory(
            @Validated @RequestParam("categoryId") Long categoryId,
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        // 1. Tìm xem category có tồn tại để sửa không
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), 
                    HttpStatus.BAD_REQUEST
            );
        }

        Category existingCategory = optCategory.get();

        // 2. Nếu người dùng chọn file icon mới thì lưu file mới và cập nhật tên ảnh
        if (icon != null && !icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            existingCategory.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, existingCategory.getIcon());
        }
        // Lưu ý: Nếu icon == null hoặc rỗng thì CategoryServiceImpl.save() sẽ tự động giữ nguyên ảnh cũ

        existingCategory.setCategoryName(categoryName);
        categoryService.save(existingCategory);

        return new ResponseEntity<Response>(
                new Response(true, "Cập nhật Thành công", existingCategory), 
                HttpStatus.OK
        );
    }

    /**
     * API 5: Xóa danh mục theo ID
     * Phương thức: DELETE
     * URL: http://localhost:8080/api/category/deleteCategory?categoryId=1
     */
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Không tìm thấy Category", null), 
                    HttpStatus.BAD_REQUEST
            );
        }

        // Thực hiện xóa danh mục khỏi CSDL
        categoryService.delete(optCategory.get());

        return new ResponseEntity<Response>(
                new Response(true, "Xóa Thành công", optCategory.get()), 
                HttpStatus.OK
        );
    }
}
