package samul.shopper.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {
    private long id;
    private String vendorName;
    private String email;
    private String country;
    private String phoneNumber;
}
