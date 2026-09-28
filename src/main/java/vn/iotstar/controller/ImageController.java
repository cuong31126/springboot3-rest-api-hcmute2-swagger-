/**
 * TÊN FILE: ImageController.java
 * CHỨC NĂNG: Controller phục vụ việc tải và hiển thị hình ảnh đã upload lên giao diện HTML / JSP.
 * DÀNH CHO NGƯỜI MỚI:
 * - Khi trình duyệt đọc thẻ <img src="/admin/categories/images/ten_anh.jpg">,
 *   nó sẽ gửi một request GET đến server.
 * - Controller này nhận tên file, gọi storageService.loadAsResource(filename)
 *   để đọc file từ thư mục "uploads" và bắn dữ liệu ảnh về cho trình duyệt hiển thị.
 */
package vn.iotstar.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.service.IStorageService;

@Controller
public class ImageController {

    @Autowired
    private IStorageService storageService;

    /**
     * Endpoint hiển thị ảnh cho Categories
     * URL: /admin/categories/images/{filename}
     */
    @GetMapping("/admin/categories/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveCategoryFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    /**
     * Endpoint hiển thị ảnh cho Products (và đường dẫn ngắn /images/{filename})
     * URL: /admin/products/images/{filename} hoặc /images/{filename}
     */
    @GetMapping({"/admin/products/images/{filename:.+}", "/images/{filename:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> serveProductFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

    /**
     * Endpoint tải file ảnh lên server (dùng chung khi thêm / sửa Category và Product)
     * URL: POST /api/upload
     */
    @PostMapping("/api/upload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "File rỗng"));
        }
        try {
            String uuid = UUID.randomUUID().toString();
            String storedFilename = storageService.getSorageFilename(file, uuid);
            storageService.store(file, storedFilename);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "filename", storedFilename,
                    "url", "/images/" + storedFilename
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
