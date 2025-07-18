package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Category;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final EntitiesService entitiesService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(EntitiesService entitiesService, ProductRepository productRepository) {
        this.entitiesService = entitiesService;
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findProductsByCategoryId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return entitiesService.getTopPopularProducts(limit);
    }
}
