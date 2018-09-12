package janari.burgers.repository;

import janari.burgers.model.Photo;
import janari.burgers.model.Venue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends PagingAndSortingRepository<Photo, String> {
}
