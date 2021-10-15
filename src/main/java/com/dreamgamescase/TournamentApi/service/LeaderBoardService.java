package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
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

    public List<LeaderBoardModel> getLeaderboardRequest(int groupId) {

        List<TournamentMapping> map = _tournamentMappingRepository.getGroupUser(groupId);
        List<LeaderBoardModel> leaderBoard = mapping(map);
        return leaderBoard;
    }

    public int getRankRequest(int userId) {
        List<LeaderBoardModel> leaderBoard = new ArrayList<LeaderBoardModel>();
        int gid = _tournamentMappingRepository.getGroupId(userId);
        List<TournamentMapping> map = _tournamentMappingRepository.getGroupUser(gid);
        leaderBoard = mapping(map);
        return leaderBoard.stream().map(aa -> aa.getUserid()).collect(Collectors.toList()).indexOf(userId) + 1;
    }

    private List<LeaderBoardModel> mapping(List<TournamentMapping> list) {
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
