package com.dreamgamescase.TournamentApi.service.data;

import com.dreamgamescase.TournamentApi.model.UserModel;

public interface UserDao {

    UserModel saveClaimRewardRequest(int userId, int rewardCoin);
}
