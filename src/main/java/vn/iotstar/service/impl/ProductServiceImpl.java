/**
 * TÊN FILE: ProductServiceImpl.java
 * CHỨC NĂNG: Cài đặt chi tiết các nghiệp vụ trong interface IProductService.
 */
package vn.iotstar.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByProductName(String name) {
        return productRepository.findByProductName(name);
    }

    @Override
    public Optional<Product> findByCreateDate(Date createAt) {
        return productRepository.findByCreateDate(createAt);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByProductNameContaining(String name) {
        return productRepository.findByProductNameContaining(name);
    }

    @Override
    public Page<Product> findByProductNameContaining(String name, Pageable pageable) {
        return productRepository.findByProductNameContaining(name, pageable);
    }

    @Override
    public List<Product> findByOrderByUnitPriceAsc() {
        return productRepository.findByOrderByUnitPriceAsc();
    }

    @Override
    public List<Product> findByCategory_CategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    public Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory_CategoryId(categoryId, pageable);
    }

    @Override
    public Page<Product> findByProductNameContainingAndCategory_CategoryId(String name, Long categoryId, Pageable pageable) {
        return productRepository.findByProductNameContainingAndCategory_CategoryId(name, categoryId, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
