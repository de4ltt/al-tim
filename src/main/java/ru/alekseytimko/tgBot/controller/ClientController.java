package ru.alekseytimko.tgBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.service.ClientService;
import ru.alekseytimko.tgBot.service.EntitiesService;

import java.util.List;

@RestController
@RequestMapping("/rest/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}/orders")
    List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return clientService.getClientOrders(id);
    }

    @GetMapping("/{id}/products")
    List<Product> getClientOrdersProducts(@PathVariable Long id) {
        return clientService.getClientProducts(id);
    }
}
