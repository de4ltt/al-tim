package ru.alekseytimko.tgBot.service;

import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;

public interface ClientOrderService {
    ClientOrder getActiveOrder(Client client);
    ClientOrder createOrder(Long clientId);
    void closeOrder(Long orderId);
}
