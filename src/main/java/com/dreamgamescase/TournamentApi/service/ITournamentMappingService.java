package com.dreamgamescase.TournamentApi.service;

import com.dreamgamescase.TournamentApi.model.LeaderBoardModel;
import com.dreamgamescase.TournamentApi.model.TournamentMapping;
import java.util.List;

public interface ITournamentMappingService {

    List<LeaderBoardModel> enterTournament(int userId, int groupId, String username);

    TournamentMapping updateTournamentScore(int userId);

    boolean isClaimedBefore(int userId);

    boolean isEntered(int userId);

    int getGroupIdByUser(int userId);

}
