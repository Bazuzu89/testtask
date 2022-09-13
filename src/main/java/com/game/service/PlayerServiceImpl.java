package com.game.service;

import com.game.DAO.DAOInterface;
import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PlayerServiceImpl implements PlayerService {
    DAOInterface<Player> playerDao;

    public PlayerServiceImpl(DAOInterface<Player> playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player[] getPlayers(HttpServletRequest request) {
        return new Player[0];
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
        return null;
    }
}
