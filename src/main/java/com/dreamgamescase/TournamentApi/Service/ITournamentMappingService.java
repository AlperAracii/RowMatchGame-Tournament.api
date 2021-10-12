package com.dreamgamescase.TournamentApi.Service;

import com.dreamgamescase.TournamentApi.Model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.Model.TournamentMapping;


import java.util.List;

public interface ITournamentMappingService {

    List<LeaderBoardModel> EnterTournament(int userid, int groupid, String username);

    TournamentMapping UpdateTournamentScore(int userid);

    boolean isClaimedBefore(int userid);

    boolean isEntered(int userid);

    int GetGroupIdByUser(int userid);

}
