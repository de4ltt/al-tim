package ru.alekseytimko.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.OrderProduct;
import ru.alekseytimko.tgBot.entity.Product;
import ru.alekseytimko.tgBot.repository.OrderProductRepository;

import java.util.List;

@Transactional
@Service
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public void addProductToOrder(ClientOrder clientOrder, Product product) {
        OrderProduct orderProduct = orderProductRepository.findByClientOrderAndProduct(clientOrder, product);

        if (orderProduct != null) {
            orderProduct.setCountProduct(orderProduct.getCountProduct() + 1);
            orderProductRepository.save(orderProduct);
        } else {
            OrderProduct newOrderProduct = new OrderProduct();
            newOrderProduct.setProduct(product);
            newOrderProduct.setClientOrder(clientOrder);
            newOrderProduct.setCountProduct(0);
            orderProductRepository.save(newOrderProduct);
        }
    }

    @Override
    public List<Product> getProductsInOrder(ClientOrder clientOrder) {
        return orderProductRepository.findProductsByClientOrder(clientOrder);
    }
}
