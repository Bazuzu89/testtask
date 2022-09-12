package com.game.DAO;

import com.game.entity.Player;

import java.util.List;

public interface DAOInterface<T> {
    T create(T entity);
    List<T> all();
    T get(Long id);
    T update(T entity);
    void delete(Long id);

}
