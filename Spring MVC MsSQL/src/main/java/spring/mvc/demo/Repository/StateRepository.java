package spring.mvc.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.mvc.demo.Models.StateModel;

import java.util.List;

public interface StateRepository extends JpaRepository<StateModel, Long> {
    @Query("select Id as Id,Name as Name from State where CityId = :cityId")
    List<StateModel> GetById(@Param("cityId")int id);
}
