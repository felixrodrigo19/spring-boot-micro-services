package br.com.msproject.orders.repository;

import br.com.msproject.orders.model.Order;
import br.com.msproject.orders.model.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o from Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
    Optional<Order> findByIdAndWithItems(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Order o set o.status = :status WHERE o = :order")
    void updateStatus(Status status, Order order);
}
