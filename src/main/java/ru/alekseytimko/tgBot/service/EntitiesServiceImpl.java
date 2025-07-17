package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.repository.ClientOrderRepository;
import ru.alekseytimko.tgBot.repository.ClientRepository;
import ru.alekseytimko.tgBot.repository.OrderProductRepository;
import ru.alekseytimko.tgBot.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
public class EntitiesServiceImpl implements EntitiesService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findProductsByCategoryId(id);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRepository.findClientOrdersByClientId(id);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
        return orderProductRepository.findProductsByClientId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return orderProductRepository.findTopPopularProducts(limit);
    }

    @Override
    public List<Client> searchClientsByName(String name) {
        return EntitiesService.super.searchClientsByName(name);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return EntitiesService.super.searchProductsByName(name);
    }
}
