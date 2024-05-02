package samul.shopper.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samul.shopper.helpers.OrderProductId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OrderProductId.class)
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @Column(name = "orderid")
    private Long orderId;

    @Id
    @Column(name = "productid")
    private Long productId;

    private int amount;
}
