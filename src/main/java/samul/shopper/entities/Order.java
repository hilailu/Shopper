package samul.shopper.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Long id;

    @Column(name = "orderstatus")
    private String orderStatus;

    @Column(name = "totalprice")
    private Double totalPrice;

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerid")
    private Customer customer;
}
