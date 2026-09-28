/**
 * TÊN FILE: IProductService.java
 * CHỨC NĂNG: Interface định nghĩa các nghiệp vụ cho đối tượng Product (Sản phẩm).
 * DÀNH CHO NGƯỜI MỚI:
 * - Định nghĩa các hàm nghiệp vụ để Controller có thể gọi khi thao tác với sản phẩm.
 */
package vn.iotstar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.iotstar.entity.Product;

public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByProductName(String name);

    Optional<Product> findByCreateDate(Date createAt);

    Product save(Product product);

    void deleteById(Long id);

    List<Product> findByProductNameContaining(String name);

    Page<Product> findByProductNameContaining(String name, Pageable pageable);

    List<Product> findByOrderByUnitPriceAsc();

    List<Product> findByCategory_CategoryId(Long categoryId);

    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingAndCategory_CategoryId(String name, Long categoryId, Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}
