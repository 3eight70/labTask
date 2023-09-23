package shift.labTask.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shift.labTask.IntervalClass.ourInterval;

@Repository
public interface IntervalRepository extends JpaRepository<ourInterval, Long> {

    @Query(value = "SELECT * FROM our_interval ORDER BY first_interval ASC, last_interval ASC LIMIT 1", nativeQuery = true)
    ourInterval findMinInterval();
}
