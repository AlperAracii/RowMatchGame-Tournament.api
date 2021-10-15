package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import java.util.List;

public interface ILeaderBoardService {

    List<LeaderBoardModel> getLeaderboardRequest(int groupid);

    int getRankRequest(int userid);
}
