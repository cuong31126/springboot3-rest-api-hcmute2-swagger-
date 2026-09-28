/**
 * TÊN FILE: ProductRepository.java
 * CHỨC NĂNG: Tầng giao tiếp và truy vấn CSDL cho bảng sản phẩm (Products).
 * DÀNH CHO NGƯỜI MỚI:
 * - Kế thừa JpaRepository<Product, Long> để có sẵn toàn bộ chức năng CRUD cho bảng Products.
 * - Khai báo thêm các hàm tìm kiếm theo tên, phân trang, và tìm theo ngày tạo.
 */
package vn.iotstar.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;

@Repository // Đánh dấu đây là Spring Data JPA Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Tìm kiếm sản phẩm theo tên gần đúng (chứa từ khóa).
     * Tương đương SQL: WHERE productName LIKE %name%
     */
    List<Product> findByProductNameContaining(String name);

    /**
     * Tìm kiếm sản phẩm theo tên gần đúng kết hợp phân trang dữ liệu.
     */
    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    /**
     * Tìm sản phẩm theo tên chính xác (dùng để kiểm tra trùng tên sản phẩm khi thêm mới).
     */
    Optional<Product> findByProductName(String name);

    /**
     * Tìm sản phẩm theo mốc thời gian tạo (createDate).
     * Dùng để lấy lại bản ghi vừa thêm mới vào CSDL theo timestamp.
     */
    Optional<Product> findByCreateDate(Date createAt);

    /**
     * Lấy tất cả sản phẩm sắp xếp theo đơn giá từ thấp đến cao (Mục 5 GraphQL - Home).
     */
    List<Product> findByOrderByUnitPriceAsc();

    /**
     * Lấy tất cả sản phẩm thuộc 1 Category cụ thể (Mục 5 GraphQL - Home).
     */
    List<Product> findByCategory_CategoryId(Long categoryId);

    /**
     * Lấy sản phẩm thuộc 1 Category có phân trang.
     */
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    /**
     * Tìm kiếm sản phẩm theo tên trong một Category cụ thể có phân trang.
     */
    Page<Product> findByProductNameContainingAndCategory_CategoryId(String name, Long categoryId, Pageable pageable);
}
