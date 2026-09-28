/**
 * TÊN FILE: CategoryServiceImpl.java
 * CHỨC NĂNG: Cài đặt chi tiết các nghiệp vụ trong interface ICategoryService.
 * DÀNH CHO NGƯỜI MỚI:
 * - Đánh dấu @Service để Spring tự động quản lý Bean này.
 * - Class này gọi CategoryRepository để truy xuất dữ liệu từ CSDL,
 *   đồng thời xử lý logic đặc thù: khi người dùng sửa Category mà không tải ảnh mới lên
 *   thì hàm save() sẽ tự động giữ lại đường dẫn ảnh cũ thay vì bị ghi đè thành null.
 */
package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.ICategoryService;

@Service // Báo cho Spring Boot biết đây là lớp xử lý nghiệp vụ (Service layer)
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Nghiệp vụ lưu danh mục:
     * - Nếu là thêm mới (categoryId == null): Lưu trực tiếp vào CSDL.
     * - Nếu là cập nhật: Kiểm tra nếu không upload icon mới thì giữ nguyên icon cũ từ CSDL.
     */
    @Override
    public <S extends Category> S save(S entity) {
        if (entity.getCategoryId() == null) {
            return categoryRepository.save(entity);
        } else {
            Optional<Category> opt = findById(entity.getCategoryId());
            if (opt.isPresent()) {
                // Nếu icon mới bị trống thì giữ lại icon cũ
                if (!StringUtils.hasText(entity.getIcon())) {
                    entity.setIcon(opt.get().getIcon());
                }
            }
            return categoryRepository.save(entity);
        }
    }

    @Override
    public Optional<Category> findByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public List<Category> findAllById(Iterable<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return categoryRepository.findOne(example);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void delete(Category entity) {
        categoryRepository.delete(entity);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(name, pageable);
    }
}
