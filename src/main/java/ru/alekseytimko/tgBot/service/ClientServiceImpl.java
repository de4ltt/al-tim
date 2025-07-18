package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private EntitiesService entitiesService;

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return entitiesService.getClientOrders(id);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
        return entitiesService.getClientProducts(id);
    }
}
