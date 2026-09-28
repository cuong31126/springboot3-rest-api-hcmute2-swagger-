/**
 * TÊN FILE: Category.java
 * CHỨC NĂNG: Thực thể (Entity) đại diện cho bảng danh mục sản phẩm (Categories) trong CSDL SQL Server.
 * DÀNH CHO NGƯỜI MỚI: File này giúp bạn mô tả bảng Database bằng code Java.
 * Khi chạy ứng dụng, Spring Data JPA sẽ tự động tạo bảng "Categories" với các cột tương ứng.
 */
package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự động tạo getter, setter, toString, equals, hashCode (Lombok)
@AllArgsConstructor // Tạo constructor có đầy đủ tất cả tham số
@NoArgsConstructor // Tạo constructor mặc định không có tham số
@Entity // Đánh dấu đây là một thực thể JPA được ánh xạ vào CSDL
@Table(name = "Categories") // Tên bảng trong CSDL SQL Server
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Khóa chính (Primary Key) của bảng Category.
     * GenerationType.IDENTITY: Tự động tăng giá trị (Identity 1, 1 trong SQL Server).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    /**
     * Tên danh mục (ví dụ: Áo sơ mi, Giày thể thao...).
     * columnDefinition = "nvarchar(200)": Hỗ trợ lưu tiếng Việt có dấu trong SQL Server.
     */
    @Column(name = "categoryName", columnDefinition = "nvarchar(200)")
    private String categoryName;

    /**
     * Tên hoặc đường dẫn của file ảnh đại diện (icon) của danh mục.
     */
    @Column(name = "icon", length = 500)
    private String icon;

    /**
     * Mối quan hệ 1 - Nhiều (1 Danh mục có Nhiều Sản phẩm).
     * mappedBy = "category": Liên kết tới biến "category" bên class Product.
     * cascade = CascadeType.ALL: Khi xóa/sửa category thì các product liên quan cũng tự động cập nhật.
     * @JsonIgnore: CỰC KỲ QUAN TRỌNG - Ngăn Jackson tuần tự hóa ngược lại, tránh vòng lặp vô tận (StackOverflowError).
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;
}
