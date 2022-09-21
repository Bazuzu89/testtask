package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.InvalidPlayerParametersException;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface PlayerService {

    Page<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder playerOrder, Integer pageNumber, Integer pageSize);

    Integer getCount(HttpServletRequest request);

    Player create(Player player) throws InvalidPlayerParametersException, ParseException;

    void delete(Long id);

    Player update(Long id);

    Player get(Long id);
}
