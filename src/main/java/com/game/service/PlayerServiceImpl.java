package com.game.service;

import com.game.DAO.DAOInterface;
import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.InvalidPlayerParametersException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        return playerDao.getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, playerOrder, pageNumber, pageSize);
    }

    @Override
    public Integer getCount(HttpServletRequest request) {
        return null;
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
        Integer level = (int) Math.round((Math.sqrt(2500 + 200 * experience) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        return playerDao.create(player);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Player update(Long id) {
        return null;
    }

    @Override
    public Player get(Long id) {
        return playerDao.get(id);
    }
}
