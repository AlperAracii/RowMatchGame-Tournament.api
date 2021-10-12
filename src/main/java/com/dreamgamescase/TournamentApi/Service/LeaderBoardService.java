package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.Model.TournamentMapping;
import com.dreamgamescase.TournamentApi.Repository.ITournamentMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderBoardService implements ILeaderBoardService {

    private final ITournamentMappingRepository _tournamentMappingRepository;

    @Autowired
    public LeaderBoardService(ITournamentMappingRepository tournamentMappingRepository) {
        _tournamentMappingRepository = tournamentMappingRepository;
    }

    public List<LeaderBoardModel> GetLeaderboardRequest(int groupid) {

        List<TournamentMapping> map = _tournamentMappingRepository.getGroupUser(groupid);
        List<LeaderBoardModel> leaderBoard = Mapping(map);
        return leaderBoard;
    }

    public int GetRankRequest(int userid) {
        List<LeaderBoardModel> leaderBoard = new ArrayList<LeaderBoardModel>();
        int gid = _tournamentMappingRepository.getGroupId(userid);
        List<TournamentMapping> map = _tournamentMappingRepository.getGroupUser(gid);
        leaderBoard = Mapping(map);
        return leaderBoard.stream().map(aa -> aa.getUserid()).collect(Collectors.toList()).indexOf(userid) + 1;
    }

    private List<LeaderBoardModel> Mapping(List<TournamentMapping> list) {
        List<LeaderBoardModel> leaderBoard = new ArrayList<LeaderBoardModel>();

        for (TournamentMapping aa : list) {
            LeaderBoardModel lb = new LeaderBoardModel();
            lb.setUserid(aa.getUserid());
            lb.setUsername(aa.getUsername());
            lb.setScore(aa.getScore());

            leaderBoard.add(lb);
        }

        return leaderBoard;
    }
}
