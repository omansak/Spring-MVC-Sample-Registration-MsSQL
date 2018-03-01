package spring.mvc.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.mvc.demo.Models.StateModel;
import spring.mvc.demo.Models.TokenModel;
import spring.mvc.demo.Models.UserViewModel;

import java.util.List;

public interface UserRepository extends JpaRepository<UserViewModel, Long> {
    @Query(value = "SELECT TOP 1 * FROM Users WHERE Email = :email", nativeQuery = true)
    UserViewModel GetUser(@Param("email") String email);
}
