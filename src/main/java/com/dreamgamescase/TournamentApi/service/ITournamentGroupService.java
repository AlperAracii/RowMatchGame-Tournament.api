package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.TournamentGroup;
import com.dreamgamescase.TournamentApi.model.UserModel;
import org.springframework.http.ResponseEntity;

public interface ITournamentGroupService {

    TournamentGroup createGroup(int segment);

    TournamentGroup checkedExistGroupBySegment(Integer segment);

    boolean isActive(int groupId);

    void updateCapacity(int groupId);

    ResponseEntity<UserModel> claimRewardRequest(int userid);
}

