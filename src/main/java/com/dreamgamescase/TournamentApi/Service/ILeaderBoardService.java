package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.LeaderBoardModel;
import java.util.List;

public interface ILeaderBoardService {

    List<LeaderBoardModel> GetLeaderboardRequest(int groupid);

    int GetRankRequest(int userid);
}
