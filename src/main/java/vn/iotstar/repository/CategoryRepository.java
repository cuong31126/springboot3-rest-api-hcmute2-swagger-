/**
 * TÊN FILE: CategoryRepository.java
 * CHỨC NĂNG: Tầng giao tiếp và truy vấn CSDL cho bảng danh mục (Categories).
 * DÀNH CHO NGƯỜI MỚI:
 * - Khi kế thừa JpaRepository<Category, Long>, Spring Data JPA sẽ TỰ ĐỘNG cung cấp
 *   sẵn cho bạn các hàm cơ bản: save() (thêm/sửa), findAll() (lấy hết), findById() (tìm theo ID),
 *   deleteById() (xóa theo ID), count() (đếm số lượng)... mà BẠN KHÔNG CẦN VIẾT 1 DÒNG SQL NÀO!
 * - Bạn chỉ cần khai báo thêm các hàm tìm kiếm tùy biến theo quy tắc đặt tên (Query Creation).
 */
package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Category;

@Repository // Đánh dấu đây là Bean tầng Repository (Spring tự động quản lý và tiêm phụ thuộc)
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Tìm kiếm danh mục theo từ khóa gần đúng trong tên (tương đương lệnh: WHERE categoryName LIKE %name%).
     * @param name: Từ khóa người dùng gõ tìm kiếm.
     * @return Danh sách các Category khớp với từ khóa.
     */
    List<Category> findByCategoryNameContaining(String name);

    /**
     * Tìm kiếm danh mục theo tên có kèm phân trang (Pageable).
     * @param name: Từ khóa tìm kiếm.
     * @param pageable: Chứa số trang (page) và số lượng bản ghi mỗi trang (size).
     * @return Một đối tượng Page chứa danh sách kết quả và thông tin tổng số trang.
     */
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

    /**
     * Tìm kiếm chính xác danh mục theo tên.
     * Dùng để kiểm tra trùng lặp tên danh mục khi người dùng bấm thêm mới.
     * @param name: Tên danh mục cần kiểm tra.
     * @return Optional<Category> (có dữ liệu nếu tìm thấy, hoặc rỗng nếu chưa có).
     */
    Optional<Category> findByCategoryName(String name);
}
