package vn.iotstar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Đường dẫn thư mục lưu trữ file tải lên (mặc định lấy từ application.properties)
     */
    private String location = "uploads";
}
