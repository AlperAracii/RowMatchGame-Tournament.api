package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class TournamentMappinServiceTest {

    private TournamentMappinService tournamentMappingService;

    private ITournamentMappingRepository tournamentMappingRepository;
    private ILeaderBoardService leaderBoardService;
    private ITournamentGroupService tournamentGroupService;

    @BeforeEach
    public void setUp() {
        tournamentMappingRepository = Mockito.mock(ITournamentMappingRepository.class);
        leaderBoardService = Mockito.mock(ILeaderBoardService.class);
        tournamentGroupService = Mockito.mock(ITournamentGroupService.class);

        tournamentMappingService = new TournamentMappinService(tournamentMappingRepository, leaderBoardService, tournamentGroupService);
    }

    @Test
    public void whenEnterTournamentCalledWithValidRequest_itShouldReturnValidLeaderBoard() {
        int userId = 5;
        int groupId = 1;
        String username = "AlperA";

        TournamentMapping newEnter = new TournamentMapping();
        newEnter.setGroupid(groupId);
        newEnter.setUserid(userId);
        newEnter.setUsername(username);
        newEnter.setScore(0);
        newEnter.setIsClaimed(false);
        newEnter.setIsactive(true);

        List<LeaderBoardModel> leaderBoard = new ArrayList<>();

        Mockito.when(tournamentMappingRepository.save(newEnter)).thenReturn(newEnter);
        Mockito.when(leaderBoardService.getLeaderboardRequest(groupId)).thenReturn(leaderBoard);

        List<LeaderBoardModel> result = leaderBoardService.getLeaderboardRequest(groupId);
        Assert.assertEquals(result, leaderBoard);
    }
}