package ru.alekseytimko.tgBot.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.alekseytimko.tgBot.entity.Client;
import ru.alekseytimko.tgBot.entity.ClientOrder;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "client-orders", path = "client-orders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    List<ClientOrder> findClientOrdersByClientId(Long id);

    ClientOrder findFirstByClientAndStatusOrderByIdDesc(Client client, Integer status);

    @Modifying
    @Query("UPDATE ClientOrder o SET o.status = 2 WHERE o.id = :orderId")
    void closeOrder(@Param("orderId") Long orderId);
}
