package samul.shopper.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendorid")
    private Long id;

    @Column(name = "vendorname")
    private String vendorName;

    @Column(name = "phonenumber")
    private String phoneNumber;

    private String email;

    private String country;
}
