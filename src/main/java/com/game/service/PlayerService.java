package com.game.service;

import com.game.entity.Player;

import javax.servlet.http.HttpServletRequest;

public interface PlayerService {

    Player[] getPlayers(HttpServletRequest request);

    Integer getCount(HttpServletRequest request);

    Player create(Player player);

    void delete(Long id);

    Player update(Long id);

    Player get(Long id);
}
