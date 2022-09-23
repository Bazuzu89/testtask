package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.InvalidInputException;
import com.game.exception.InvalidPlayerParametersException;
import com.game.exception.PlayerNotFoundException;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface PlayerService {

    Page<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder playerOrder, Integer pageNumber, Integer pageSize);

    Integer getCount(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel);

    Player create(Player player) throws InvalidPlayerParametersException, ParseException;

    void delete(Long id) throws PlayerNotFoundException, InvalidInputException;

    Player update(Long id, Player player) throws PlayerNotFoundException, InvalidInputException, ParseException;

    Player get(Long id) throws InvalidInputException, PlayerNotFoundException;
}
