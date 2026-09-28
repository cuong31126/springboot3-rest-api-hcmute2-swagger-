/**
 * TÊN FILE: VietApiApplication.java
 * CHỨC NĂNG: Lớp khởi chạy chính (Main Class / Entry Point) của toàn bộ dự án Spring Boot.
 * DÀNH CHO NGƯỜI MỚI: 
 * - Đây là file có hàm main() để bấm Run As -> Spring Boot App trong STS/Eclipse/IntelliJ.
 * - Khi ứng dụng bật lên, nó sẽ nạp toàn bộ cấu hình, kết nối CSDL, tạo bảng tự động,
 *   và kích hoạt hàm init() để tạo sẵn thư mục chứa file upload ("uploads").
 */
package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import vn.iotstar.config.StorageProperties;
import vn.iotstar.service.IStorageService;

@SpringBootApplication // Đánh dấu đây là ứng dụng Spring Boot (kích hoạt Auto-configuration và Component Scan)
@EnableConfigurationProperties(StorageProperties.class) // Kích hoạt nạp cấu hình StorageProperties từ application.properties
public class VietApiApplication {

    /**
     * Điểm xuất phát của chương trình Java.
     */
    public static void main(String[] args) {
        SpringApplication.run(VietApiApplication.class, args);
    }

    /**
     * @Bean CommandLineRunner: Đoạn mã này sẽ TỰ ĐỘNG CHẠY ngay sau khi ứng dụng Spring Boot khởi động xong.
     * Chức năng: Gọi storageService.init() để kiểm tra và tự động tạo thư mục "uploads" trên ổ cứng.
     * Nếu không có đoạn này, khi user upload ảnh đầu tiên mà chưa có thư mục thì sẽ bị lỗi "Directory Not Found".
     */
    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args -> {
            storageService.init();
        });
    }
}
