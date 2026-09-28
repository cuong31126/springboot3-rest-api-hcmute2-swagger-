/**
 * TÊN FILE: Product.java
 * CHỨC NĂNG: Thực thể (Entity) đại diện cho bảng sản phẩm (Products) trong CSDL SQL Server.
 * DÀNH CHO NGƯỜI MỚI: File này định nghĩa cấu trúc dữ liệu của một Sản phẩm.
 * Mỗi sản phẩm sẽ thuộc về một Category (thông qua khóa ngoại categoryId).
 */
package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Tự sinh getter/setter, toString, equals, hashCode
@AllArgsConstructor // Tạo constructor có toàn bộ tham số
@NoArgsConstructor // Tạo constructor rỗng
@Entity // Khai báo đây là Entity tương ứng với 1 bảng trong DB
@Table(name = "Products") // Tên bảng lưu trữ trong SQL Server
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Khóa chính tự động tăng của sản phẩm.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    /**
     * Tên sản phẩm, kiểu nvarchar(500) hỗ trợ tiếng Việt có dấu, bắt buộc không được null.
     */
    @Column(name = "productName", length = 500, columnDefinition = "nvarchar(500) not null")
    private String productName;

    /**
     * Số lượng hàng tồn kho.
     */
    @Column(name = "quantity", nullable = false)
    private int quantity;

    /**
     * Đơn giá bán của sản phẩm.
     */
    @Column(name = "unitPrice", nullable = false)
    private double unitPrice;

    /**
     * Tên file ảnh hoặc đường dẫn hình ảnh đại diện sản phẩm.
     */
    @Column(length = 200)
    private String images;

    /**
     * Mô tả chi tiết về sản phẩm (hỗ trợ tiếng Việt).
     */
    @Column(columnDefinition = "nvarchar(500) not null")
    private String description;

    /**
     * Phần trăm hoặc số tiền giảm giá.
     */
    @Column(nullable = false)
    private double discount;

    /**
     * Ngày tạo sản phẩm trong hệ thống.
     * @Temporal: Chỉ định kiểu lưu trữ thời gian TIMESTAMP (cả ngày và giờ).
     * @DateTimeFormat: Định dạng chuẩn chuỗi ngày giờ khi bind dữ liệu.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * Trạng thái sản phẩm (ví dụ: 1: Còn bán, 0: Ngừng kinh doanh).
     */
    @Column(nullable = false)
    private short status;

    /**
     * Mối quan hệ Nhiều - 1 (Nhiều Sản phẩm thuộc về 1 Danh mục).
     * @JoinColumn(name = "categoryId"): Tạo cột khóa ngoại tên "categoryId" trong bảng Products.
     * @JsonIgnore: Tránh lặp vô hạn khi chuyển Product sang JSON và ngược lại.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
