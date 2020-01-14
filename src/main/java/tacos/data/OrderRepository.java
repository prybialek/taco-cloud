package tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
