package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.TournamentGroup;
import com.dreamgamescase.TournamentApi.Model.UserModel;


public interface ITournamentGroupService {

    TournamentGroup CreateGroup(int segment);

    TournamentGroup ChackedExistGroupBySegment(Integer segment);

    boolean isActive(int groupid);

    void UpdateCapacity(int groupid);

    UserModel ClaimRewardRequest(int userid);
}

