package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.TournamentGroup;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import com.dreamgamescase.TournamentApi.model.UserModel;
import com.dreamgamescase.TournamentApi.repository.ITournamentGroupRepository;
import com.dreamgamescase.TournamentApi.repository.ITournamentMappingRepository;
import com.dreamgamescase.TournamentApi.service.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TournamentGroupService implements ITournamentGroupService {

    private final ITournamentGroupRepository tournamentGroupRepository;
    private final ILeaderBoardService leaderBoardService;
    private final ITournamentMappingRepository tournamentMappingRepository;
    private final UserDao userDao;

    @Autowired
    public TournamentGroupService(ITournamentGroupRepository tournamentGroupRepository, ILeaderBoardService leaderBoardService, ITournamentMappingRepository tournamentMappingRepository, UserDao userDao) {
        this.tournamentGroupRepository = tournamentGroupRepository;
        this.leaderBoardService = leaderBoardService;
        this.tournamentMappingRepository = tournamentMappingRepository;
        this.userDao = userDao;
    }

    @Override
    public TournamentGroup createGroup(int segment) {

        TournamentGroup group = new TournamentGroup();
        group.setSegment(segment);
        group.setCapacity(0);
        group.setIsactive(true);

        TournamentGroup creaete = tournamentGroupRepository.save(group);

        return creaete;
    }

    @Override
    public TournamentGroup checkedExistGroupBySegment(Integer segment) {
        boolean isExist = tournamentGroupRepository.isExistGroup(segment);
        TournamentGroup group;
        if (isExist) {
            group = tournamentGroupRepository.findGroupBySegment(segment);
        } else {
            group = new TournamentGroup();
        }
        return group;
    }

    public boolean isActive(int groupId) {
        return tournamentGroupRepository.isActiveGroup(groupId);
    }

    public void updateCapacity(int groupId) {

        TournamentGroup checked = tournamentGroupRepository.findGroupCapacity(groupId);
        if (checked != null)
            checked.setCapacity(checked.getCapacity() + 1);
        else
            tournamentGroupRepository.save(new TournamentGroup());
    }

    public ResponseEntity<UserModel> claimRewardRequest(int userId) {

        TournamentMapping user = tournamentMappingRepository.getUserInMapping(userId);
        if (user != null) {
            if (user.getIsClaimed() == false) {
                int rank = leaderBoardService.getRankRequest(userId);
                int rewardCoin = 0;
                if (rank == 1) rewardCoin = 10000;
                else if (rank == 2) rewardCoin = 5000;
                else if (rank == 3) rewardCoin = 3000;
                else if (rank >= 4 && rank <= 10) rewardCoin = 1000;
                else rewardCoin = 0;
                user.setIsClaimed(true);
                tournamentMappingRepository.save(user);
                UserModel model = userDao.saveClaimRewardRequest(userId, rewardCoin);
                return ResponseEntity.accepted().body(model);
            } else
                return new ResponseEntity("Reward claimed before", HttpStatus.BAD_REQUEST);
        }else
            return new ResponseEntity("User Not Found id:" + userId, HttpStatus.BAD_REQUEST);
    }
}
