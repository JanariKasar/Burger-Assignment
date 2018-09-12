package janari.burgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    private int id;
    private String url;
    private boolean burger;

    public Photo(String url, boolean burger) {
        this.url = url;
        this.burger = burger;
    }
}
