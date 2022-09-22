package com.game.DAO;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.PlayerNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DAOInterface<T> {
    T create(T entity);
    List<T> all();
    T get(Long id) throws PlayerNotFoundException;
    T update(Long id, Player player) throws PlayerNotFoundException;
    void delete(Long id) throws PlayerNotFoundException;

    Page<T> getPlayers(String name,
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
                       Integer pageSize);
}
