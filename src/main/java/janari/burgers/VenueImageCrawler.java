package janari.burgers;

import janari.burgers.model.Venue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VenueImageCrawler {

    private final Venue venue;

    public VenueImageCrawler(Venue venue) {
        this.venue = venue;
    }

    public List<String> getImageUrls() throws IOException {
        Document doc = Jsoup.connect(getVenueUrl()).timeout(30000).get();
        Element photosBlock = doc.select(".photosBlock").first();
        if (photosBlock != null) {
            return photosBlock.children().stream()
                    .limit(16)
                    .map(element -> element.select(".mainPhoto").first().attr("src"))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private String getVenueUrl() {
        return String.format("https://foursquare.com/v/%s/photos", venue.getId());
    }
}
