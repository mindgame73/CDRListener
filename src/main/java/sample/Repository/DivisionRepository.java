package sample.Repository;

import sample.Model.Division;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends CrudRepository<Division, Integer> {
    Optional<Division> getByDivisionName(String str);
    List<Division> findByDivisionNameContainingIgnoreCase(String str);
    List<Division> findAllByOrderByDivisionNameAsc();
}
