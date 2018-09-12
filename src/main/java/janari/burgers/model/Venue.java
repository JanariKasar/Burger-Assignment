package janari.burgers.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Venue {

    @Id
    private String id;
    private String name;
    private String locationString;
    @OneToMany
    private List<Photo> images;

    public Venue(String id, String name, String locationString) {
        this.id = id;
        this.name = name;
        this.locationString = locationString;
    }
}
