package com.dreamgamescase.TournamentApi.Model;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "group_user_mapping")
public class TournamentMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "groupid")
    private Integer groupid;

    @Column(name= "userid")
    private Integer userid;

    @Column(name= "username")
    private String username;

    @Column(name= "score")
    private Integer score;

    @Column(name= "isclaimed")
    private Boolean isClaimed;

    @Column(name= "isactive")
    private Boolean isactive;

}
