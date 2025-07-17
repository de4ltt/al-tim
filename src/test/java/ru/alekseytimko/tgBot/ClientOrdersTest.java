package ru.alekseytimko.tgBot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alekseytimko.tgBot.entity.*;
import ru.alekseytimko.tgBot.repository.*;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class ClientOrdersTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createClientsAndOrders() {

        List<Product> rolls = getProductsByCategoryName("Роллы");
        List<Product> burgers = getProductsByCategoryName("Бургеры");
        List<Product> beverages = getProductsByCategoryName("Напитки");

        Client alice = saveClient("Алиса", 0L);
        Client bob = saveClient("Боб", 1L);
        Client carol = saveClient("Каролина", 2L);

        ClientOrder a1 = saveOrder(alice, 1, 0);
        saveOrderProduct(a1, rolls.getFirst(), 2);
        saveOrderProduct(a1, beverages.getFirst(), 1);

        ClientOrder a2 = saveOrder(alice, 1, 0);
        saveOrderProduct(a2, burgers.get(1), 1);
        saveOrderProduct(a2, beverages.get(3), 2);

        ClientOrder b1 = saveOrder(bob, 1, 0);
        saveOrderProduct(b1, rolls.get(3), 1);
        saveOrderProduct(b1, rolls.get(4), 1);

        ClientOrder c1 = saveOrder(carol, 1, 0);
        saveOrderProduct(c1, rolls.get(0), 3);
        saveOrderProduct(c1, rolls.get(1), 2);
        saveOrderProduct(c1, beverages.get(8), 1);
    }

    private Client saveClient(String fullName, Long externalId) {
        Client client = new Client();
        client.setFullName(fullName);
        client.setPhoneNumber(generateRandomPhoneNumber());
        client.setExternalId(externalId);
        client.setAddress("г. Пример, ул. Пушкина, д. Колотушкина");
        return clientRepository.save(client);
    }

    private String generateRandomPhoneNumber() {
        Random random = new Random();
        return "+7" + (900 + random.nextInt(100)) + String.format("%07d", random.nextInt(1_000_0000));
    }

    private ClientOrder saveOrder(Client client, int status, double total) {
        ClientOrder order = new ClientOrder();
        order.setClient(client);
        order.setStatus(status);
        order.setTotal(total);
        return clientOrderRepository.save(order);
    }

    private void saveOrderProduct(ClientOrder order, Product product, int count) {
        OrderProduct op = new OrderProduct();
        op.setClientOrder(order);
        op.setProduct(product);
        op.setCountProduct(count);
        orderProductRepository.save(op);
    }

    private List<Product> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        return productRepository.findProductsByCategoryId(category.getId());
    }
}

