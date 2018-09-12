package janari.burgers;

import janari.burgers.foursquare.service.FourSquareService;
import janari.burgers.model.Photo;
import janari.burgers.model.Venue;
import janari.burgers.repository.PhotoRepository;
import janari.burgers.repository.VenueRepository;
import janari.burgers.service.BurgerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private FourSquareService service;
    @Autowired
    private BurgerImageService burgerImageService;
    @Autowired
    private VenueRepository repository;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        List<Venue> venues = service.fetchVenues();

        long start = System.currentTimeMillis();
        System.out.println("Starting image crawling...");

        for (Venue venue : venues) {
            System.out.println("Getting images from " + venue.getName());
            try {
                List<String> images = new VenueImageCrawler(venue).getImageUrls();
                List<String> burgers = burgerImageService.questionBurgerGods(images);

                venue.setImages(images.stream()
                        .map(url -> new Photo(url, burgers.contains(url)))
                        .sorted(Comparator.comparing(Photo::isBurger).reversed())
                        .collect(Collectors.toList()));
                photoRepository.saveAll(venue.getImages());

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            repository.save(venue);
        }
        System.out.println("Image crawling complete (" + (System.currentTimeMillis() - start) + "ms)");
    }

}