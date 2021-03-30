package ee.knpractice.demoProjectKn.repository;

import ee.knpractice.demoProjectKn.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    EmployeeEntity findById(long id);
    List<EmployeeEntity> findByFirstnameContaining(String firstname);



}
