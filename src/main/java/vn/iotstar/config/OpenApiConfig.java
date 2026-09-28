/**
 * TÊN FILE: OpenApiConfig.java
 * CHỨC NĂNG: Cấu hình giao diện Swagger 3 / OpenAPI UI cho toàn bộ hệ thống API.
 * DÀNH CHO NGƯỜI MỚI:
 * - Swagger / OpenAPI là công cụ tự động sinh ra một trang web tài liệu trực quan (Interactive Documentation).
 * - Thay vì phải nhớ URL và dùng Postman để gõ tay, bạn chỉ cần mở trình duyệt vào:
 *       http://localhost:8080/swagger-ui.html (hoặc /swagger-ui/index.html)
 * - Trang web này sẽ liệt kê đầy đủ tất cả API (GET, POST, PUT, DELETE),
 *   cho phép bạn bấm nút "Try it out" để gửi dữ liệu test thử và xem kết quả JSON trả về ngay lập tức!
 */
package vn.iotstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration // Đánh dấu đây là class cấu hình (Configuration Bean) của Spring
public class OpenApiConfig {

    /**
     * Khởi tạo Bean OpenAPI tùy chỉnh thông tin tài liệu API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Cấu hình thông tin mô tả dự án
                .info(new Info()
                        .title("API Documentation - VietApi Spring Boot 3")
                        .version("1.0.0")
                        .description("Tài liệu tương tác RESTful API CRUD Category & Product có hỗ trợ Upload File hình ảnh (Theo giáo trình ThS. Nguyễn Hữu Trung - HCMUTE)")
                        .contact(new Contact()
                                .name("Khoa CNTT - ĐH Sư Phạm Kỹ Thuật TP.HCM")
                                .email("trungnh@hcmute.edu.vn")
                                .url("https://www.youtube.com/@baigiai"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                // Cấu hình URL server mặc định
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ));
    }
}
