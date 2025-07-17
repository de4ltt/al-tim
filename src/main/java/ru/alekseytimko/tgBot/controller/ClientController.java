package ru.alekseytimko.tgBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.service.EntitiesService;

import java.util.List;

@RestController
@RequestMapping("/rest/clients")
public class ClientController {

    @Autowired
    private EntitiesService entitiesService;

    @GetMapping("/{id}/orders")
    List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return entitiesService.getClientOrders(id);
    }

    @GetMapping("/{id}/products")
    List<Product> getClientOrdersProducts(@PathVariable Long id) {
        return entitiesService.getClientProducts(id);
    }
}
