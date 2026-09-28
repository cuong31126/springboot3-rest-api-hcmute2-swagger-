/**
 * TÊN FILE: Response.java
 * CHỨC NĂNG: Lớp định dạng dữ liệu trả về (Response Model) chuẩn cho tất cả các RESTful API.
 * DÀNH CHO NGƯỜI MỚI: 
 * Thay vì trả dữ liệu một cách lộn xộn, ta đóng gói vào class này để phía Client (React, Vue, Postman, Web)
 * luôn luôn nhận về 1 format đồng nhất gồm 3 thông tin:
 *   1. status: Thành công (true) hay Thất bại (false)
 *   2. message: Câu thông báo rõ ràng (ví dụ: "Thêm thành công", "Không tìm thấy danh mục")
 *   3. body: Dữ liệu thực tế kèm theo (Danh sách danh mục, chi tiết đối tượng, hoặc null nếu lỗi)
 */
package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự động tạo getter, setter, toString (Lombok)
@AllArgsConstructor // Tạo constructor có 3 tham số (status, message, body)
@NoArgsConstructor // Tạo constructor rỗng mặc định
public class Response {

    /**
     * Trạng thái thực thi của API:
     * true: Gọi API thành công
     * false: Thất bại hoặc có lỗi nghiệp vụ
     */
    private Boolean status;

    /**
     * Thông điệp phản hồi bằng chữ để hiển thị cho người dùng hoặc lập trình viên dễ đọc.
     */
    private String message;

    /**
     * Nội dung dữ liệu chính trả về.
     * Sử dụng kiểu Object để có thể chứa bất kỳ kiểu dữ liệu nào: List<Category>, 1 Category, chuỗi, hoặc null.
     */
    private Object body;
}
