package sample.Repository;

import sample.Model.CallDetailRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CallDetailRecordRepository extends CrudRepository<CallDetailRecord, Integer> {
}
