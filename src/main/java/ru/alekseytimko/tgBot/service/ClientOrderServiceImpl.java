package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.repository.ClientOrderRepository;
import ru.alekseytimko.tgBot.repository.ClientRepository;

@Service
@Transactional
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepository clientOrderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientOrderServiceImpl(ClientOrderRepository clientOrderRepository, ClientRepository clientRepository) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientOrder getActiveOrder(Client client) {
        return clientOrderRepository.findFirstByClientAndStatusOrderByIdDesc(client, 1);
    }

    @Override
    public ClientOrder createOrder(Long clientId) {

        Client client = clientRepository.findById(clientId).orElseThrow();

        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrder.setStatus(1);
        clientOrder.setTotal(0.0d);

        return clientOrderRepository.save(clientOrder);
    }

    @Override
    public void closeOrder(Long orderId) {
        clientOrderRepository.closeOrder(orderId);
    }
}
