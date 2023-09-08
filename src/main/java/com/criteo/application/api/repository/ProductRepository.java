package com.criteo.application.api.repository;

import com.criteo.application.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author poliakoveliezer ProductRepository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findTopByCategoryOrderByBidDesc(String category);

    Product findTopByOrderByBidDesc();

    List<Product> findByCodeIn(List<String> codes);
}
