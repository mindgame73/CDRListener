package sample.Repository;

import sample.Model.ResultCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultCodeRepository extends CrudRepository<ResultCode, Integer> {
    Optional<ResultCode> getResultCodeByResultCode(String string);
}
