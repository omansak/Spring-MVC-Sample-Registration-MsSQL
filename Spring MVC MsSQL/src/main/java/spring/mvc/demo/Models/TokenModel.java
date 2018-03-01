package spring.mvc.demo.Models;

import javax.persistence.*;

@Entity(name = "Tokens")
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "int")
    private int Id;
    @Column(name = "Token", columnDefinition = "text")
    private String Token;
    @Column(name = "Email", columnDefinition = "nvarchar(100)")
    private String Email;
    @Column(name = "Type", columnDefinition = "int")
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
