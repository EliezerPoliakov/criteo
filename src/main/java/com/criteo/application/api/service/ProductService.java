package com.criteo.application.api.service;

import com.criteo.application.api.entity.Product;
import com.criteo.application.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author poliakoveliezer Product Service
 */
@Service
public class ProductService {

    private static final int PRODUCTS_COUNT = 10;
    private static final List<String> CATEGORIES = List.of("books", "cars", "computers");
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 12;
    final private Random random = new SecureRandom();
    @Autowired
    private ProductRepository productRepository;

    public Product serveAd(String category) {
        Product topProductByCategory = productRepository.findTopByCategoryOrderByBidDesc(category);
        if (topProductByCategory != null) return topProductByCategory;
        return productRepository.findTopByOrderByBidDesc();

    }

    public void updateBidForCampaignProducts(List<String> codes, double bid) {
        List<Product> productList = productRepository.findByCodeIn(codes);
        productList.forEach(product -> product.setBid(product.getBid() + bid));
        productRepository.saveAll(productList);
    }

    private char getRandomChar() {
        return ALPHABET.charAt(random.nextInt(ALPHABET.length()));
    }

    private String getRandomCategory() {
        return CATEGORIES.get(random.nextInt(CATEGORIES.size()));
    }

    private double getRandomPrice() {
        return random.nextInt(100) + random.nextInt(100) / 100.;
    }

    private String getRandomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    /**
     * createDefaultProducts call happens only once
     */
    @PostConstruct
    private void createDefaultProducts() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < PRODUCTS_COUNT; i++)
            productList.add(new Product(getRandomCode(), "product " + i, getRandomCategory(), getRandomPrice()));
        productRepository.saveAll(productList);
    }

    public List<String> getCategories() {
        return CATEGORIES;
    }

    public List<String> getCodes() {
        return productRepository.findAll().stream().map(Product::getCode).collect(Collectors.toList());
    }
}
