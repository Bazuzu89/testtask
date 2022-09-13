package com.game.DAO;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDAO implements DAOInterface<Player> {

    private JpaRepository<Player, Long> repository;

    public PlayerDAO(JpaRepository<Player, Long> repository) {
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
}
