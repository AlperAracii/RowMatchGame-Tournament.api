package com.dreamgamescase.TournamentApi.Repository;


import com.dreamgamescase.TournamentApi.Model.TournamentMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ITournamentMappingRepository extends JpaRepository<TournamentMapping,Integer> {

    @Query(value = " SELECT * FROM group_user_mapping u WHERE u.groupid = ?1 ORDER BY score", nativeQuery = true)
    List<TournamentMapping> getGroupUser (Integer groupid);

    @Query(value = "SELECT u.groupid FROM group_user_mapping u WHERE u.userid = ?1 AND u.isactive=true", nativeQuery = true)
    int getGroupId (Integer userid);

    @Query(value = "SELECT * FROM group_user_mapping u WHERE u.userid = ?1 AND u.isactive = true", nativeQuery = true)
    TournamentMapping getUserInMapping (Integer userid);

    @Query(value = "SELECT count(u.isclaimed)>0 FROM group_user_mapping u WHERE u.userid = ?1 AND u.isactive = false AND u.isclaimed = false", nativeQuery = true)
     int isClaimed (Integer userid);

    @Query(value = "SELECT count(u.userid)>0 FROM group_user_mapping u WHERE u.userid = ?1 AND u.isactive = true", nativeQuery = true)
    int isEntered (Integer userid);
}
