package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.model.UserModel;
import com.dreamgamescase.TournamentApi.repository.ITournamentGroupRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import com.dreamgamescase.TournamentApi.service.data.UserDao;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TournamentGroupServiceTest {

    private TournamentGroupService tournamentGroupService;

    private  ITournamentGroupRepository tournamentGroupRepository;
    private  ILeaderBoardService leaderBoardService;
    private  ITournamentMappingRepository tournamentMappingRepository;
    private  UserDao userDao;

    @BeforeEach
    public void setUp() {
        tournamentGroupRepository = Mockito.mock(ITournamentGroupRepository.class);
        leaderBoardService = Mockito.mock(ILeaderBoardService.class);
        tournamentMappingRepository = Mockito.mock(ITournamentMappingRepository.class);
        userDao = Mockito.mock(UserDao.class);

        tournamentGroupService = new TournamentGroupService(tournamentGroupRepository, leaderBoardService,tournamentMappingRepository,userDao);
    }

    @Test
    public void whenClaimRewardRequestCalledWithValidRequest_itShouldReturnValidUserModel() {
        int userId = 8;
        int groupId = 1;
        int rewardCoin = 5000;
        String username = "AlperA";

        UserModel newEnter = new UserModel();
        newEnter.setUserid(userId);
        newEnter.setUsername("AlperA");
        newEnter.setLevel(125);
        newEnter.setCoin(2000);
        newEnter.setSegment(2);

        TournamentMapping user = new TournamentMapping();
        user.setGroupid(groupId);
        user.setUserid(userId);
        user.setUsername(username);
        user.setScore(0);
        user.setIsClaimed(false);
        user.setIsactive(true);

        Mockito.when(tournamentMappingRepository.getUserInMapping(userId)).thenReturn(user);
        Mockito.when(leaderBoardService.getRankRequest(userId)).thenReturn(1);
        Mockito.when(tournamentMappingRepository.save(user)).thenReturn(user);
        Mockito.when(userDao.saveClaimRewardRequest(userId, rewardCoin)).thenReturn(newEnter);

        ResponseEntity<UserModel> result = tournamentGroupService.claimRewardRequest(userId);
        Assert.assertEquals(result, newEnter);
        //Servisten userDao ile dönerken testte null dönüyor bu nedenle hata var ama postman ile test ettiğimde çalışıyor.
    }

    @Test
    public void whenClaimRewardRequestCalledWithInvalidUserId_itShouldReturnErrorMessage() {
        int userId = 5;

        Mockito.when(tournamentMappingRepository.getUserInMapping(userId)).thenReturn(null);
        ResponseEntity<UserModel> result = tournamentGroupService.claimRewardRequest(userId);
        Assert.assertEquals(result, new ResponseEntity("User Not Found id:" + userId, HttpStatus.BAD_REQUEST));
    }

    @Test
    public void whenClaimRewardRequestCalledWithClaimedRewardBefore_itShouldReturnErrorMessage() {
        int userId = 8;
        int groupId = 1;
        String username = "AlperA";
        TournamentMapping user = new TournamentMapping();
        user.setGroupid(groupId);
        user.setUserid(userId);
        user.setUsername(username);
        user.setScore(0);
        user.setIsClaimed(true);
        user.setIsactive(true);

        Mockito.when(tournamentMappingRepository.getUserInMapping(userId)).thenReturn(user);
        ResponseEntity<UserModel> result = tournamentGroupService.claimRewardRequest(userId);
        Assert.assertEquals(result, new ResponseEntity("Reward claimed before", HttpStatus.BAD_REQUEST));
    }
}