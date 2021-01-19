package platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends CrudRepository<Code, Integer> {

    @Query(nativeQuery = true, value = "SELECT TOP 10 * FROM CODE WHERE TIME_RESTRICTED = 0 AND VIEW_RESTRICTED = 0 ORDER BY DATE DESC")
    List<Code> findTop10LatestCode();

    Optional<Code> findCodeByCode(Code code);

    Optional<Code> findByUuid(UUID id);

    @Transactional
    void deleteByUuid(UUID id);

}
