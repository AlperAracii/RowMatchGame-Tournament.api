package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TournamentMappinService implements ITournamentMappingService {

    private final ITournamentMappingRepository tournamentMappingRepository;
    private final ILeaderBoardService leaderBoardService;
    private final ITournamentGroupService tournamentGroupService;

    @Autowired
    public TournamentMappinService(ITournamentMappingRepository TournamentMappingRepository, ILeaderBoardService leaderBoardService, ITournamentGroupService tournamentGroupService) {
        tournamentMappingRepository = TournamentMappingRepository;
        this.leaderBoardService = leaderBoardService;
        this.tournamentGroupService = tournamentGroupService;
    }

    @Override
    public List<LeaderBoardModel> enterTournament(int userid, int groupId, String username){

        TournamentMapping newEnter = new TournamentMapping();
        newEnter.setGroupid(groupId);
        newEnter.setUserid(userid);
        newEnter.setUsername(username);
        newEnter.setScore(0);
        newEnter.setIsClaimed(false);
        newEnter.setIsactive(true);
        tournamentMappingRepository.save(newEnter);
        tournamentGroupService.updateCapacity(groupId);

        return leaderBoardService.getLeaderboardRequest(groupId);
    }

    public TournamentMapping updateTournamentScore(int userId){
        TournamentMapping tm = tournamentMappingRepository.getUserInMapping(userId);
        tm.setScore(tm.getScore()+1);
        return tournamentMappingRepository.save(tm);
    }

    public boolean isClaimedBefore(int userId){

        return tournamentMappingRepository.isClaimed(userId) <=0;
    }

    public boolean isEntered(int userId){
        return tournamentMappingRepository.isEntered(userId) > 0;
    }

    public int getGroupIdByUser(int userId){

        return tournamentMappingRepository.getGroupId(userId);
    }

}
