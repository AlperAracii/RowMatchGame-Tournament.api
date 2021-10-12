package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.TournamentGroup;
import com.dreamgamescase.TournamentApi.Model.UserModel;
import com.dreamgamescase.TournamentApi.Repository.ITournamentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TournamentGroupService implements ITournamentGroupService {

    private static final String BASE_URL = "http://localhost:8080";
    RestTemplate restTemplate = new RestTemplate();
    private final ITournamentGroupRepository _tournamentGroupRepository;
    private final ILeaderBoardService _leaderBoardService;

    @Autowired
    public TournamentGroupService(ITournamentGroupRepository tournamentGroupRepository, ILeaderBoardService leaderBoardService) {
        _tournamentGroupRepository = tournamentGroupRepository;
        _leaderBoardService = leaderBoardService;
    }

    @Override
    public TournamentGroup CreateGroup(int segment) {

        TournamentGroup group = new TournamentGroup();
        group.setSegment(segment);
        group.setCapacity(0);
        group.setIsactive(true);

        TournamentGroup creaete = _tournamentGroupRepository.save(group);

        return creaete;
    }

    @Override
    public TournamentGroup ChackedExistGroupBySegment(Integer segment) {
        boolean isExist = _tournamentGroupRepository.isExistGroup(segment);
        TournamentGroup group;
        if (isExist) {
            group = _tournamentGroupRepository.findGroupBySegment(segment);
        } else {
            group = new TournamentGroup();
        }
        return group;
    }

    public boolean isActive(int groupid) {
        return _tournamentGroupRepository.isActiveGroup(groupid);
    }

    public void UpdateCapacity(int groupid) {

        TournamentGroup tg = _tournamentGroupRepository.findGroupById(groupid);
        if (tg != null)
            tg.setCapacity(tg.getCapacity() + 1);

        _tournamentGroupRepository.save(tg);
    }

    public UserModel ClaimRewardRequest(int userid) {

        int rank = _leaderBoardService.GetRankRequest(userid);
        int rewardCoin = 0;

        if (rank == 1) rewardCoin = 10000;
        else if (rank == 2) rewardCoin = 5000;
        else if(rank == 3) rewardCoin = 3000;
        else if (rank >=4 && rank <=10) rewardCoin = 1000;
        else rewardCoin = 0;

        String uri = BASE_URL + "/user/claim-reward/" + userid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Integer> map= new LinkedMultiValueMap<>();
        map.add("coin", rewardCoin);
        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<>(map, headers);
        UserModel result = restTemplate.postForObject(uri,request, UserModel.class);

        return result;
    }
}
