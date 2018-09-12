package janari.burgers.repository;

import janari.burgers.model.Venue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends PagingAndSortingRepository<Venue, String> {
}
