package com.game.DAO;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.exception.PlayerNotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class PlayerDAO implements DAOInterface<Player> {

    private PlayerRepository repository;


    public PlayerDAO(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Player create(Player entity) {
        return repository.save(entity);
    }

    @Override
    public List<Player> all() {
        return repository.findAll();
    }

    @Override
    public Player get(Long id) throws PlayerNotFoundException {
        try {
            Player player = repository.findById(id).get();
            return player;
        } catch (NoSuchElementException e) {
            throw new PlayerNotFoundException();
        }
    }

    @Override
    public Player update(Long id, Player player) throws PlayerNotFoundException {
        try {
            Player foundPlayer = repository.findById(id).get();
            if (player.getName() != null && !player.getName().equals("")) {
                foundPlayer.setName(player.getName());
            }
            if (player.getTitle() != null && !player.getTitle().equals("")) {
                foundPlayer.setTitle(player.getTitle());
            }
            if (player.getRace() != null && player.getRace() != null) {
                foundPlayer.setRace(player.getRace());
            }
            if (player.getProfession() != null && player.getProfession() != null) {
                foundPlayer.setProfession(player.getProfession());
            }
            if (player.getBanned() != null && player.getBanned() != null) {
                foundPlayer.setBanned(player.getBanned());
            }
            if (player.getExperience() != null) {
                foundPlayer.setExperience(player.getExperience());
                Integer level = (int) Math.floor((Math.sqrt(2500 + 200 * foundPlayer.getExperience()) - 50) / 100);
                Integer untilNextLevel = 50 * (level + 1) * (level + 2) - foundPlayer.getExperience();
                foundPlayer.setLevel(level);
                foundPlayer.setUntilNextLevel(untilNextLevel);
            }
            if (player.getBirthday() != null) {
                foundPlayer.setBirthday(player.getBirthday());
            }
            return repository.save(foundPlayer);
        } catch (NoSuchElementException e) {
            throw new PlayerNotFoundException();
        }
    }

    @Override
    public void delete(Long id) throws PlayerNotFoundException {
        try {
            Player player = repository.findById(id).get();
            repository.delete(player);
        } catch (NoSuchElementException e) {
            throw new PlayerNotFoundException();
        }
    }

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
        return repository.findAll(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                PageRequest.of(0, 20, Sort.by("id").ascending())
        );
    }
}
