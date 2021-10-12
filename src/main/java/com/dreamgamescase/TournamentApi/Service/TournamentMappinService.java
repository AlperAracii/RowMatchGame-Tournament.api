package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.Model.TournamentMapping;
import com.dreamgamescase.TournamentApi.Repository.ITournamentMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentMappinService implements ITournamentMappingService {

    private final ITournamentMappingRepository _tournamentMappingRepository;
    private final ILeaderBoardService _leaderBoardService;
    private final ITournamentGroupService _tournamentGroupService;

    @Autowired
    public TournamentMappinService(ITournamentMappingRepository TournamentMappingRepository, ILeaderBoardService leaderBoardService, ITournamentGroupService tournamentGroupService) {
        _tournamentMappingRepository = TournamentMappingRepository;
        _leaderBoardService = leaderBoardService;
        _tournamentGroupService = tournamentGroupService;
    }

    @Override
    public List<LeaderBoardModel> EnterTournament(int userid, int groupid, String username){

        TournamentMapping newEnter = new TournamentMapping();
        newEnter.setGroupid(groupid);
        newEnter.setUserid(userid);
        newEnter.setUsername(username);
        newEnter.setScore(0);
        newEnter.setIsClaimed(false);
        newEnter.setIsactive(true);
        TournamentMapping map = _tournamentMappingRepository.save(newEnter);
        _tournamentGroupService.UpdateCapacity(groupid);

        return _leaderBoardService.GetLeaderboardRequest(groupid);
    }

    public void ClaimRewardRequest(int userid){
        TournamentMapping user = _tournamentMappingRepository.getUserInMapping(userid);
    }

    public TournamentMapping UpdateTournamentScore(int userid){
        TournamentMapping tm = _tournamentMappingRepository.getUserInMapping(userid);
        tm.setScore(tm.getScore()+1);
        return _tournamentMappingRepository.save(tm);
    }

    public boolean isClaimedBefore(int userid){
        int result = _tournamentMappingRepository.isClaimed(userid);
        boolean flag = true;
        if(result > 0)
            flag = false;
        return flag;
    }

    public boolean isEntered(int userid){
        int result = _tournamentMappingRepository.isEntered(userid);
        return intToBool(result);
    }

    public int GetGroupIdByUser(int userid){
        return _tournamentMappingRepository.getGroupId(userid);
    }

    private boolean intToBool(int result){
        boolean flag = false;
        if(result >0)
            flag = true;
        else if(result == 0)
            flag = false;
        return flag ;
    }
}
