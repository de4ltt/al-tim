package ru.alekseytimko.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.alekseytimko.tgBot.entity.ClientOrder;
import ru.alekseytimko.tgBot.entity.OrderProduct;
import ru.alekseytimko.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order-products", path = "order-products")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("""
                 SELECT op.product FROM OrderProduct op
                 WHERE op.clientOrder.client.id = :clientId
            """)
    List<Product> findProductsByClientId(@Param("clientId") Long clientId);

    @Query(value = """
                SELECT p.*
                FROM order_product op
                JOIN product p ON op.product_id = p.id
                GROUP BY op.product_id
                ORDER BY SUM(op.count_product) DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<Product> findTopPopularProducts(@Param("limit") Integer limit);

    OrderProduct findByClientOrderAndProduct(ClientOrder clientOrder, Product product);

    @Query("""
            SELECT op.product FROM OrderProduct op
            WHERE op.clientOrder = :clientOrder
            """)
    List<Product> findProductsByClientOrder(@Param("clientOrder") ClientOrder clientOrder);
}
