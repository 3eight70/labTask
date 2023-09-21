package shift.labTask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervalRepository extends JpaRepository<ourInterval, Long> {

    @Query(value = "SELECT * FROM our_interval ORDER BY first_interval ASC, last_interval ASC LIMIT 1", nativeQuery = true)
    ourInterval findMinInterval();
}
