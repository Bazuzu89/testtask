package com.game.DAO;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DAOInterface<T> {
    T create(T entity);
    List<T> all();
    T get(Long id);
    T update(T entity);
    void delete(Long id);

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
