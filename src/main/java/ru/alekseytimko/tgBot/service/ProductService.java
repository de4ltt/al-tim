package ru.alekseytimko.tgBot.service;

import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategoryId(Long id);
    List<Product> getTopPopularProducts(Integer limit);
}
