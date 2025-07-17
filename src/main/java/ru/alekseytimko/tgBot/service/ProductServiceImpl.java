package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

@Service
@Transactional
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
