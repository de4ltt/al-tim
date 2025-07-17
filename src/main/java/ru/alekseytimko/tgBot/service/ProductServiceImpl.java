package ru.alekseytimko.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private EntitiesService entitiesService;

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return entitiesService.getProductsByCategoryId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return entitiesService.getTopPopularProducts(limit);
    }
}
