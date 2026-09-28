/**
 * TÊN FILE: WebCategoryController.java
 * CHỨC NĂNG: Controller điều hướng trả về giao diện JSP cho người dùng.
 * DÀNH CHO NGƯỜI MỚI:
 * - Khác với @RestController (trả về JSON), @Controller này trả về tên file giao diện.
 * - Khi bạn mở trình duyệt gõ: http://localhost:8080/ hoặc http://localhost:8080/admin/category
 *   nó sẽ tìm và mở file /WEB-INF/views/admin/category-ajax.jsp.
 */
package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebCategoryController {

    /**
     * Trang chủ GraphQL E-Commerce (Home): Lọc Category, Sắp xếp theo giá, AJAX Thymeleaf
     */
    @GetMapping({"/", "/home"})
    public String showHomePage() {
        return "home";
    }

    /**
     * Trang Quản trị (Admin): CRUD, Phân trang, Tìm kiếm Category & Product bằng GraphQL
     */
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    /**
     * Giữ nguyên các đường dẫn trang tĩnh RESTful AJAX cũ để bảo toàn tương thích
     */
    @GetMapping({"/admin/category", "/category-ajax"})
    public String showCategoryAjaxPage() {
        return "redirect:/category-ajax.html";
    }

    @GetMapping("/product-ajax")
    public String showProductAjaxPage() {
        return "redirect:/product-ajax.html";
    }
}
