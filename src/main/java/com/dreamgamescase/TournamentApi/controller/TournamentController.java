package com.dreamgamescase.TournamentApi.controller;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentGroup;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.model.UserModel;
import com.dreamgamescase.TournamentApi.service.ILeaderBoardService;
import com.dreamgamescase.TournamentApi.service.ITournamentGroupService;
import com.dreamgamescase.TournamentApi.service.ITournamentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("tournament")
public class TournamentController {

    private final ITournamentGroupService tournamentGroupService;
    private final ITournamentMappingService tournamentMappingService;
    private final ILeaderBoardService leaderBoardService;

    @Autowired
    public TournamentController(ITournamentGroupService tournamentGroupService, ITournamentMappingService tournamentMappingService, ILeaderBoardService leaderBoardService) {
        this.tournamentGroupService = tournamentGroupService;
        this.tournamentMappingService = tournamentMappingService;
        this.leaderBoardService = leaderBoardService;
    }

    @PostMapping("/enter")
    public ResponseEntity<List<LeaderBoardModel>> enterTournamentRequest(@ModelAttribute UserModel user) {
        List<LeaderBoardModel> leaderBoard = new ArrayList<LeaderBoardModel>();
        boolean flag = tournamentMappingService.isEntered(user.getUserid());
        if (!flag) {
            boolean isClaimed = tournamentMappingService.isClaimedBefore(user.getUserid());
            if (isClaimed == true && user.getSegment() > 0 && user.getCoin() > 1000) {
                TournamentGroup isExist = tournamentGroupService.checkedExistGroupBySegment(user.getSegment());

                if (isExist.getGroupid() == null) {
                    TournamentGroup group = tournamentGroupService.createGroup(user.getSegment());
                    leaderBoard = tournamentMappingService.enterTournament(user.getUserid(), group.getGroupid(), user.getUsername());
                } else {
                    leaderBoard = tournamentMappingService.enterTournament(user.getUserid(), isExist.getGroupid(), user.getUsername());
                }
                return ResponseEntity.accepted().body(leaderBoard);
            }
            else
                return new ResponseEntity("Requirements for the tournament not met", HttpStatus.BAD_REQUEST);
        } else {
            int gid = tournamentMappingService.getGroupIdByUser(user.getUserid());
            leaderBoard = leaderBoardService.getLeaderboardRequest(gid);
            return ResponseEntity.accepted().body(leaderBoard);
        }
    }

    @GetMapping("/get-leaderboard/{groupId}")
    public List<LeaderBoardModel> getLeaderboardRequest(@PathVariable int groupId) {
        return leaderBoardService.getLeaderboardRequest(groupId);
    }

    @GetMapping("/get-rank/{userId}")
    public int getRankRequest(@PathVariable int userId) {
        return leaderBoardService.getRankRequest(userId);
    }

    @PostMapping("/update-score/{userId}")
    public TournamentMapping updateTournamentScore(@PathVariable int userId) {
        return tournamentMappingService.updateTournamentScore(userId);
    }

    @PostMapping("/claim-reward/{userId}")
    public ResponseEntity<UserModel> claimRewardCoin(@PathVariable int userId) {
        return tournamentGroupService.claimRewardRequest(userId);
    }
}
