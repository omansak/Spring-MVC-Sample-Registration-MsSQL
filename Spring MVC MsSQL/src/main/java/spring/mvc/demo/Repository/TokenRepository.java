package spring.mvc.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import spring.mvc.demo.Models.TokenModel;

import javax.persistence.*;
import java.util.List;

public interface TokenRepository extends JpaRepository<TokenModel, Long> {
    @Query(value = "SELECT TOP 1 * FROM Tokens WHERE Email = :email AND Type = :type", nativeQuery = true)
    TokenModel GetToken(@Param("email") String email, @Param("type") int type);
}
