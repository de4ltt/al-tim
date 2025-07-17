package ru.alekseytimko.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

public interface ClientService {
    List<ClientOrder> getClientOrders(Long id);
    List<Product> getClientProducts(Long id);
}
