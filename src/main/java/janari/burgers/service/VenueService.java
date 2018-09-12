package janari.burgers.service;

import janari.burgers.model.Venue;
import janari.burgers.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository repository;

    public List<Venue> getVenues() {
        return (List<Venue>) repository.findAll();
    }

}
