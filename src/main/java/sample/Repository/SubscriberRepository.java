package sample.Repository;

import sample.Model.Subscriber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Integer> {
    Optional<Subscriber> findByExternalNum(Long ext_num);
}
