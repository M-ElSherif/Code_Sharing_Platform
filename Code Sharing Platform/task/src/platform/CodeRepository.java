package platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends CrudRepository<Code, Integer> {

    @Query(nativeQuery = true, value = "SELECT TOP 10 * FROM CODE ORDER BY CODEID DESC")
    List<Code> findTop10LatestCode();
    Code findCodeByCode(Code code);
}
