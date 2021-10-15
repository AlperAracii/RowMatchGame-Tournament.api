package com.dreamgamescase.TournamentApi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class UserModel implements Serializable {

    private Integer userid;
    private String username;
    private Integer level;
    private Integer coin;
    private Integer segment;

}
