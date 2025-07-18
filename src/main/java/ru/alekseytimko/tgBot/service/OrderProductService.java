package ru.alekseytimko.tgBot.service;

import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

public interface OrderProductService {
    void addProductToOrder(ClientOrder clientOrder, Product product);
    List<Product> getProductsInOrder(ClientOrder clientOrder);
}
