package com.dreamgamescase.TournamentApi.service;

/**
 * Turnuvaların nasıl schedule edileceğini belirler
 */
public interface ITournamentSchedulerService {

    void scheduleToStart();

    void scheduleToFinish();
}
