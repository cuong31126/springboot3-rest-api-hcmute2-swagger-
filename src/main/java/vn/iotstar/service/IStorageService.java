package vn.iotstar.service;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    /**
     * Khởi tạo thư mục lưu trữ nếu chưa tồn tại
     */
    void init();

    /**
     * Xóa file khỏi hệ thống lưu trữ
     */
    void delete(String storeFilename) throws Exception;

    /**
     * Tải Path đại diện cho file
     */
    Path load(String filename);

    /**
     * Đọc file dưới dạng Resource phục vụ việc tải / hiển thị ảnh
     */
    Resource loadAsResource(String filename);

    /**
     * Lưu trữ file từ MultipartFile lên đĩa
     */
    void store(MultipartFile file, String storeFilename);

    /**
     * Tạo tên file chuẩn hóa kết hợp id (ví dụ: p<id>.<extension>)
     */
    String getSorageFilename(MultipartFile file, String id);
}
