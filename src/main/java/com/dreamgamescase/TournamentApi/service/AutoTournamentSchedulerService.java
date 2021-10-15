package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.repository.ITournamentRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentGroupRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoTournamentSchedulerService implements ITournamentSchedulerService {

    private final ITournamentService tournamentService;

    @Autowired
    public AutoTournamentSchedulerService(ITournamentService tournamentService, ITournamentMappingRepository tournamentMappingRepository, ITournamentGroupRepository tournamentGroupRepository, ITournamentRepository tournamentRepository) {
        this.tournamentService = tournamentService;
    }

    @Override
    @Scheduled(cron = "0 0 * * *")
    public void scheduleToStart() {
        tournamentService.startTournament();
    }

    @Override
    @Scheduled(cron = "0 20 * * *")
    public void scheduleToFinish() {
        tournamentService.finishTournament();
    }
}
