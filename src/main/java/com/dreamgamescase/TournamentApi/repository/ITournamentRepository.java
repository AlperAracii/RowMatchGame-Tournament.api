package com.dreamgamescase.TournamentApi.repository;

import com.dreamgamescase.TournamentApi.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITournamentRepository extends JpaRepository<Tournament, String> {
    @Query(value = "SELECT * FROM tournament u WHERE u.isactive = true", nativeQuery = true)
    Tournament findActiveTournament ();
}
