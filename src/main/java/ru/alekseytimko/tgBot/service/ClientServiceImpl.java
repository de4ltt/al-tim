package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.repository.ClientRepository;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final EntitiesService entitiesService;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(EntitiesService entitiesService, ClientRepository clientRepository) {
        this.entitiesService = entitiesService;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<ClientOrder> getClientOrders(Long id) {
        return entitiesService.getClientOrders(id);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
        return entitiesService.getClientProducts(id);
    }

    @Override
    public Client getByExternalId(Long externalId) {
        return clientRepository.findByExternalId(externalId);
    }
}
