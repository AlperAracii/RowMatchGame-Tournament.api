package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.Tournament;
import com.dreamgamescase.TournamentApi.model.TournamentGroup;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.repository.ITournamentGroupRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentService implements ITournamentService {

    private final ITournamentMappingRepository tournamentMappingRepository;
    private final ITournamentGroupRepository tournamentGroupRepository;
    private final ITournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(ITournamentMappingRepository tournamentMappingRepository, ITournamentGroupRepository tournamentGroupRepository, ITournamentRepository tournamentRepository) {
        this.tournamentMappingRepository = tournamentMappingRepository;
        this.tournamentGroupRepository = tournamentGroupRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public void startTournament() {
        Tournament t = new Tournament();
        t.setIsactive(true);
        t.setStartdate(LocalDateTime.now());
        tournamentRepository.save(t);
    }

    @Override
    public void finishTournament() {
        List<TournamentGroup> tg = tournamentGroupRepository.findActiveGroup();
        tg.stream().forEach(aa -> aa.setIsactive(false));
        tournamentGroupRepository.saveAll(tg);

        List<TournamentMapping> tm = tournamentMappingRepository.findActiveMapping();
        tm.stream().forEach(aa -> aa.setIsactive(false));
        tournamentMappingRepository.saveAll(tm);

        Tournament tournament = tournamentRepository.findActiveTournament();
        tournament.setEnddate(LocalDateTime.now());
        tournament.setIsactive(false);
        tournamentRepository.save(tournament);
    }
}
