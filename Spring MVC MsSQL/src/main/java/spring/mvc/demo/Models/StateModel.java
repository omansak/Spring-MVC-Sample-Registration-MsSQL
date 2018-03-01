package spring.mvc.demo.Models;

import javax.persistence.*;

@Entity(name = "State")
public class StateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", columnDefinition = "int")
    private int Id;
    @Column(name = "CityId", columnDefinition = "int")
    private int CityId;
    @Column(name = "Name", columnDefinition = "nvarchar(50)")
    private String Name;
}
