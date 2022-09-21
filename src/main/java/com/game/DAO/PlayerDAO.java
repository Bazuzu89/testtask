package com.game.DAO;

import com.game.controller.PlayerOrder;
import com.game.entity.*;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Player get(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Player update(Player entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.getOne(id));
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
