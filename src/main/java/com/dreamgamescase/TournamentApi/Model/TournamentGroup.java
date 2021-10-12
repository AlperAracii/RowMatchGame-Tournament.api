package com.dreamgamescase.TournamentApi.Model;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "tournament_groups")
public class TournamentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupid;

    @Column(name= "segment")
    private Integer segment;

    @Column(name= "capacity")
    private Integer capacity;

    @Column(name= "isactive")
    private Boolean isactive;

}
