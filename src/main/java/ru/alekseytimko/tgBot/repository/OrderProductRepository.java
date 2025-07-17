package ru.alekseytimko.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query("""
                SELECT op.product
                FROM OrderProduct op
                GROUP BY op.product
                ORDER BY SUM(op.countProduct) DESC
                LIMIT = :limit
            """)
    List<Product> findTopPopularProducts(@Param("limit") Integer limit);
}
