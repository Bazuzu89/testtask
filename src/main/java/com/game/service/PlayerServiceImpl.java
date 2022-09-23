package com.game.service;

import com.game.DAO.DAOInterface;
import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.InvalidInputException;
import com.game.exception.InvalidPlayerParametersException;
import com.game.exception.PlayerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PlayerServiceImpl implements PlayerService {
    DAOInterface<Player> playerDao;

    public PlayerServiceImpl(DAOInterface<Player> playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Page<Player> getPlayers(String name,
                                   String title,
                                   Race race,
                                   Profession profession,
                                   Long after,
                                   Long before,
                                   Boolean banned,
                                   Integer minExperience,
                                   Integer maxExperience,
                                   Integer minLevel,
                                   Integer maxLevel,
                                   PlayerOrder playerOrder,
                                   Integer pageNumber,
                                   Integer pageSize) {
        if (playerOrder == null) {
            playerOrder = PlayerOrder.ID;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 3;
        }
        return playerDao.getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, playerOrder, pageNumber, pageSize);
    }

    @Override
    public Integer getCount(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        Integer count = (int) getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, null, null, null).getTotalElements();
        return count;
    }

    @Override
    public Player create(Player player) throws InvalidPlayerParametersException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date minDate = sdf.parse("2000-01-01");
        Date maxDate = sdf.parse("3000-12-31");
        if (
                (player.getName() == null || player.getName() == "" || player.getName().length() > 12)
                || (player.getTitle() == null || player.getTitle().length() > 30)
                || player.getRace() == null
                || player.getProfession() == null
                || (player.getBirthday() == null
                        || player.getBirthday().before(minDate)
                        || player.getBirthday().after(maxDate))
                || (player.getExperience() == null || player.getExperience() < 0 || player.getExperience() > 10_000_000)
        ) {
            throw new InvalidPlayerParametersException();
        }
        Integer experience = player.getExperience();
        Integer level = (int) Math.floor((Math.sqrt(2500 + 200 * experience) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        return playerDao.create(player);
    }

    @Override
    public void delete(Long id) throws PlayerNotFoundException, InvalidInputException {
        if (id != (long) id || id <= 0) {
            throw new InvalidInputException();
        }
        playerDao.delete(id);
    }

    @Override
    public Player update(Long id, Player player) throws PlayerNotFoundException, InvalidInputException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date minDate = sdf.parse("2000-01-01");
        Date maxDate = sdf.parse("3000-12-31");
        player.setId(null);
        player.setLevel(null);
        if (
                id == null || id != (long) id || id <= 0
                || (player.getName() != null && player.getName().length() > 12)
                || (player.getTitle() != null && player.getTitle().length() > 30)
                || (player.getExperience() != null
                        && (player.getExperience() < 0 || player.getExperience() > 10_000_000))
                || (player.getBirthday() != null
                        && (player.getBirthday().before(minDate)
                        || player.getBirthday().after(maxDate)))
        ) {
            throw new InvalidInputException();
        }
        return playerDao.update(id, player);
    }

    @Override
    public Player get(Long id) throws InvalidInputException, PlayerNotFoundException {
        if (id != (long) id || id <= 0) {
            throw new InvalidInputException();
        }
        return playerDao.get(id);
    }
}
