package janari.burgers.foursquare.service;

import janari.burgers.model.Venue;
import janari.burgers.model.VenueLocation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FourSquareService {

    @Value("${foursquare.id}")
    private String fourSquareId;
    @Value("${foursquare.secret}")
    private String fourSquareSecret;
    @Value("${venue.location.query}")
    private String location;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Get list venues in a location
     */
    public List<Venue> fetchVenues() {
        VenueExploreBaseResponse response = restTemplate.getForObject(createVenueSearchURI(), VenueExploreBaseResponse.class);
        return response.getResponse().getAllVenues().stream()
                .map(v -> new Venue(v.id, v.name, v.location.toString()))
                .collect(Collectors.toList());
    }

    private URI createVenueSearchURI() {
        return UriComponentsBuilder.fromHttpUrl("https://api.foursquare.com/v2/venues/explore")
                .queryParam("client_id", fourSquareId)
                .queryParam("client_secret", fourSquareSecret)
                .queryParam("near", location)
                .queryParam("categoryId", "4bf58dd8d48988d16c941735")
                .queryParam("limit", 50)
                .queryParam("v", "20180323").build().toUri();
    }

    @Data
    @NoArgsConstructor
    public static class VenueExploreBaseResponse {
        private VenueExploreResponse response;
    }

    @Data
    @NoArgsConstructor
    public static class VenueExploreResponse {
        private List<VenueGroup> groups;

        public List<FourSquareVenue> getAllVenues() {
            return groups.stream()
                    .flatMap(venueGroup -> venueGroup.getItems().stream())
                    .map(VenueGroupEntity::getVenue)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    public static class VenueGroup {
        private List<VenueGroupEntity> items;
    }

    @Data
    @NoArgsConstructor
    public static class VenueGroupEntity {
        private FourSquareVenue venue;
    }

    @Data
    @NoArgsConstructor
    public static class FourSquareVenue {
        private String id;
        private String name;
        private VenueLocation location;
    }

}
