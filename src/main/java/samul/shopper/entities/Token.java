package samul.shopper.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "Tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenid")
    private Long id;

    private String token;

    @Column(name = "expirydate")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;
}