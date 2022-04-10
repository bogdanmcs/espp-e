package com.example.springbootex.service;

import com.example.springbootex.entity.Section;
import com.example.springbootex.exception.NotFoundException;
import com.example.springbootex.entity.Product;
import com.example.springbootex.repository.ProductRepository;
import com.example.springbootex.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          SectionRepository sectionRepository) {
        this.productRepository = productRepository;
        this.sectionRepository = sectionRepository;
    }

    public Product get(int id) {
        return getProductOrThrow(id);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void add(int sectionId, Product product) {
        Section section = getSectionOrThrow(sectionId);
        product.setSection(section);
        productRepository.save(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void update(int id, Product product) {
        Product updatedProduct = getProductOrThrow(id);
        updatedProduct.setName(product.getName());
        updatedProduct.setSection(product.getSection());
        productRepository.save(updatedProduct);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void delete(int id) {
        Product product = getProductOrThrow(id);
        productRepository.delete(product);
    }

    public Product getProductOrThrow(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find product with id " + id));
    }

    public Section getSectionOrThrow(int id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find section with id " + id));
    }
}
