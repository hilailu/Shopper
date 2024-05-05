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
@Table(name = "Supply")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplyid")
    private Long id;

    private Date date;

    private Double price;

    private Integer amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendorid")
    private Vendor vendor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productid")
    private Product product;
}
