package janari.burgers.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VenueLocation {

    private String address;
    private String city;

    @Override
    public String toString() {
        if (address != null) {
            String address = this.address;
            if (city != null) {
                address += ", " + city;
            }
            return address;
        } else if (this.city != null) {
            return city;
        }
        return "";
    }
}
