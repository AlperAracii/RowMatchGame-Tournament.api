package com.dreamgamescase.TournamentApi.Controller;

import com.dreamgamescase.TournamentApi.Model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.Model.TournamentGroup;
import com.dreamgamescase.TournamentApi.Model.TournamentMapping;
import com.dreamgamescase.TournamentApi.Model.UserModel;
import com.dreamgamescase.TournamentApi.Service.ILeaderBoardService;
import com.dreamgamescase.TournamentApi.Service.ITournamentGroupService;
import com.dreamgamescase.TournamentApi.Service.ITournamentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("tournament")
public class TournamentController {

    RestTemplate restTemplate = new RestTemplate();
    private final ITournamentGroupService _tournamentGroupService;
    private final ITournamentMappingService _tournamentMappingService;
    private final ILeaderBoardService _leaderBoardService;

    @Autowired
    public TournamentController(ITournamentGroupService tournamentGroupService, ITournamentMappingService tournamentMappingService, ILeaderBoardService leaderBoardService) {
        _tournamentGroupService = tournamentGroupService;
        _tournamentMappingService = tournamentMappingService;
        _leaderBoardService = leaderBoardService;
    }

    @PostMapping("/EnterTournamentRequest")
    public List<LeaderBoardModel> EnterTournamentRequest(@ModelAttribute UserModel user) {
        List<LeaderBoardModel> leaderBoard = new ArrayList<LeaderBoardModel>();
        boolean flag = _tournamentMappingService.isEntered(user.getUserid());
        if (!flag) {
            boolean isClaimed = _tournamentMappingService.isClaimedBefore(user.getUserid());
            if (isClaimed == true && user.getSegment() > 0 && user.getCoin() > 1000) {
                TournamentGroup isExist = _tournamentGroupService.ChackedExistGroupBySegment(user.getSegment());

                if (isExist.getGroupid() == null) {
                    TournamentGroup group = _tournamentGroupService.CreateGroup(user.getSegment());
                    leaderBoard = _tournamentMappingService.EnterTournament(user.getUserid(), group.getGroupid(), user.getUsername());
                } else
                    leaderBoard = _tournamentMappingService.EnterTournament(user.getUserid(), isExist.getGroupid(), user.getUsername());
            }
        } else if (flag) {
            int gid = _tournamentMappingService.GetGroupIdByUser(user.getUserid());
            leaderBoard = _leaderBoardService.GetLeaderboardRequest(gid);
        }

        return leaderBoard;
    }

    @PostMapping("/GetLeaderboardRequest/{groupid}")
    public List<LeaderBoardModel> GetLeaderboardRequest(@PathVariable int groupid) {
        return _leaderBoardService.GetLeaderboardRequest(groupid);
    }

    @PostMapping("/GetRankRequest/{userid}")
    public int GetRankRequest(@PathVariable int userid) {
        return _leaderBoardService.GetRankRequest(userid);
    }

    @PostMapping("/UpdateTournamentScore/{userid}")
    public TournamentMapping UpdateTournamentScore(int userid) {
        return _tournamentMappingService.UpdateTournamentScore(userid);
    }

    @PostMapping("/ClaimRewardCoin/{userid}")
    public UserModel ClaimRewardCoin(@PathVariable int userid){
        return _tournamentGroupService.ClaimRewardRequest(userid);
    }
}
