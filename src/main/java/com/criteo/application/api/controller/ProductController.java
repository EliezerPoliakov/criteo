package com.criteo.application.api.controller;

import com.criteo.application.api.entity.Product;
import com.criteo.application.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author poliakoveliezer Product Controller
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /**
     * Get a single promoted product, the one with the highest bid, belonging to active campaign/s from the specified category. If there are no
     * promoted product for the matching category simply return a promoted product with the highest bid.
     */
    @GetMapping
    public @ResponseBody ResponseEntity<Product> serveAd(@RequestParam String category) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.serveAd(category));
    }

    /**
     * Get all categories.
     */
    @GetMapping("/categories")
    public @ResponseBody ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategories());
    }

    /**
     * Get all codes.
     */
    @GetMapping("/codes")
    public @ResponseBody ResponseEntity<List<String>> getCodes() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCodes());
    }
}
