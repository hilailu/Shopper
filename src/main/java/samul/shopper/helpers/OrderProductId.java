package samul.shopper.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductId implements Serializable {
    private Long orderId;
    private Long productId;
}
