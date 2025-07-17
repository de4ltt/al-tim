package ru.alekseytimko.tgBot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.service.EntitiesService;

import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class ProductController {

    private final EntitiesService entitiesService;

    public ProductController(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
    }

    @GetMapping(value = "/search", params = "categoryId")
    List<Product> getProductsByCategoryId(@RequestParam Long categoryId) {
        return entitiesService.getProductsByCategoryId(categoryId);
    }

    @GetMapping(value = "/popular", params = "limit")
    List<Product> getTopPopularProducts(@RequestParam Integer limit) {
        return entitiesService.getTopPopularProducts(limit);
    }
}
