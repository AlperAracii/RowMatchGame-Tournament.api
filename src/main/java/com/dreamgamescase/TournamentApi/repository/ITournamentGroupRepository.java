package com.dreamgamescase.TournamentApi.repository;

import com.dreamgamescase.TournamentApi.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ITournamentGroupRepository extends JpaRepository<TournamentGroup,Integer> {

    @Query(value = "SELECT count(u)>0 FROM TournamentGroup u WHERE u.segment = ?1 AND u.isactive = true AND u.capacity <20")
    boolean isExistGroup (Integer segment);

    @Query(value = "SELECT u FROM TournamentGroup u WHERE u.groupid = ?1")
    TournamentGroup findGroupById (Integer groupid);

    @Query(value = "SELECT u FROM TournamentGroup u WHERE u.segment = ?1 AND u.isactive = true AND u.capacity <20")
    TournamentGroup findGroupBySegment (Integer segment);

    @Query(value = "SELECT count(u)>0 FROM TournamentGroup u WHERE u.isactive = true AND u.groupid = ?1")
    boolean isActiveGroup (Integer groupid);

    @Query(value = "SELECT u FROM TournamentGroup u WHERE u.isactive = true AND u.groupid = ?1 AND u.capacity < 20")
    TournamentGroup findGroupCapacity (Integer groupid);

    @Query(value = "SELECT u FROM TournamentGroup u WHERE u.isactive = true")
    List<TournamentGroup> findActiveGroup ();
}
