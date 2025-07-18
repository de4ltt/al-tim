package ru.alekseytimko.tgBot.service;

import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

public interface ClientService {
    Client save(Client client);
    List<ClientOrder> getClientOrders(Long id);
    List<Product> getClientProducts(Long id);
    Client getByExternalId(Long externalId);
}
