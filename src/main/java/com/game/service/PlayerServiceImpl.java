package com.game.service;

import com.game.DAO.DAOInterface;
import com.game.controller.PlayerOrder;
import com.game.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public Player create(Player player) {
        Integer experience = player.getExperience();
        Integer level = (int) Math.round(Math.sqrt((2500 + 200 * experience) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        if (!player.getProfession().name().equals(Profession.WARRIOR.name())) {
            System.out.println(player.getProfession());
            return null;
        }
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
