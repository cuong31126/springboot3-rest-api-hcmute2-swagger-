/**
 * TÊN FILE: ICategoryService.java
 * CHỨC NĂNG: Interface định nghĩa các nghiệp vụ (Business Service) cho đối tượng Category.
 * DÀNH CHO NGƯỜI MỚI:
 * - Tầng Service là nơi chứa "luật nghiệp vụ" của bài toán (Business Logic).
 * - Interface này đóng vai trò như một bản thiết kế (hợp đồng): liệt kê các chức năng
 *   cần có (lấy danh sách, tìm kiếm, lưu, cập nhật, xóa...) trước khi class ServiceImpl đi vào cài đặt chi tiết.
 */
package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Category;

public interface ICategoryService {

    /**
     * Xóa một danh mục bằng thực thể Category truyền vào.
     */
    void delete(Category entity);

    /**
     * Xóa danh mục dựa theo ID.
     */
    void deleteById(Long id);

    /**
     * Đếm tổng số lượng danh mục hiện có trong CSDL.
     */
    long count();

    /**
     * Tìm kiếm 1 danh mục theo mẫu đối tượng (Query by Example).
     */
    <S extends Category> Optional<S> findOne(Example<S> example);

    /**
     * Tìm danh mục theo ID (trả về Optional để tránh lỗi NullPointerException).
     */
    Optional<Category> findById(Long id);

    /**
     * Lấy danh sách danh mục theo tập hợp nhiều ID.
     */
    List<Category> findAllById(Iterable<Long> ids);

    /**
     * Lấy toàn bộ danh mục có sắp xếp (Sort).
     */
    List<Category> findAll(Sort sort);

    /**
     * Lấy toàn bộ danh mục có phân trang (Pageable).
     */
    Page<Category> findAll(Pageable pageable);

    /**
     * Lấy toàn bộ danh mục không sắp xếp.
     */
    List<Category> findAll();

    /**
     * Tìm danh mục theo tên chính xác.
     */
    Optional<Category> findByCategoryName(String name);

    /**
     * Thêm mới hoặc cập nhật danh mục vào CSDL.
     * Có xử lý nghiệp vụ: Nếu cập nhật mà không chọn ảnh mới thì giữ nguyên ảnh cũ.
     */
    <S extends Category> S save(S entity);

    /**
     * Tìm kiếm danh mục theo từ khóa tên kèm phân trang.
     */
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

    /**
     * Tìm kiếm danh mục theo từ khóa tên trả về danh sách.
     */
    List<Category> findByCategoryNameContaining(String name);
}
