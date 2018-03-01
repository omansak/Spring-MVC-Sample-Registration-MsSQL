package spring.mvc.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.mvc.demo.Models.CityModel;
import spring.mvc.demo.Models.UserViewModel;

public interface CityRepository extends JpaRepository<CityModel, Long> {
}
