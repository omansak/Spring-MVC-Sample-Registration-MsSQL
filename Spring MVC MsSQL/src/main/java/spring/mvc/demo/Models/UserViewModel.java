package spring.mvc.demo.Models;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.sql.Types;

@Entity(name = "Users")
public class UserViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "int")
    private int Id;
    @Size(min = 8, message = "min : 8")
    @Column(name = "Password", columnDefinition = "nvarchar(64)")
    private String Password;
    @Size(min = 8, message = "min : 8")
    @Column(name = "ConfirmPassword", columnDefinition = "nvarchar(64)")
    private String ConfirmPassword;
    @Size(max = 100, message = "max : 8")
    @Column(name = "Email", columnDefinition = "nvarchar(100)")
    private String Email;
    @Column(name = "Name", columnDefinition = "nvarchar(50)")
    private String Name;
    @Column(name = "Surname", columnDefinition = "nvarchar(50)")
    private String Surname;
    @Column(name = "Enable")
    private boolean Enable;
    @Column(name = "CityId", columnDefinition = "int")
    private int CityId;
    @Column(name = "StateId", columnDefinition = "int")
    private int StateId;


    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean enable) {
        Enable = enable;
    }

    public boolean IsValid() {
        if (getEmail().isEmpty() || getEmail().equals(null) || (getEmail().length() > 50)) {
            return false;
        }
        if (getPassword().isEmpty() || getPassword().equals(null) || getPassword().length() < 8) {
            return false;
        }
        /*...*/
        return true;
    }
}
