package ru.alekseytimko.tgBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.service.EntitiesService;
import ru.alekseytimko.tgBot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/search", params = "categoryId")
    List<Product> getProductsByCategoryId(@RequestParam Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping(value = "/popular", params = "limit")
    List<Product> getTopPopularProducts(@RequestParam Integer limit) {
        return productService.getTopPopularProducts(limit);
    }
}
