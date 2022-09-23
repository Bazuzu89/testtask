package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT player FROM Player player " +
            "WHERE (:name IS NULL OR :name = '' OR player.name LIKE CONCAT('%',:name,'%')) " +
            "AND (:title IS NULL OR :title = '' OR player.title LIKE CONCAT('%',:title,'%')) " +
            "AND (:race IS NULL OR player.race = :race) " +
            "AND (:profession IS NULL OR player.profession = :profession) " +
            "AND (:banned IS NULL OR player.banned = :banned) " +
            "AND (:after IS NULL OR player.birthday >= :after) " +
            "AND (:before IS NULL OR player.birthday <= :before) " +
            "AND (:minExperience IS NULL OR player.experience >= :minExperience) " +
            "AND (:maxExperience IS NULL OR player.experience <= :maxExperience) " +
            "AND (:minLevel IS NULL OR :minLevel = 0 OR player.level >= :minLevel) " +
            "AND (:maxLevel IS NULL OR :maxLevel = 0 OR player.level <= :maxLevel)"
    )
    Page<Player> findAll(@Param("name") String name,
                         @Param("title") String title,
                         @Param("race") Race race,
                         @Param("profession") Profession profession,
                         @Param("after") Date after,
                         @Param("before") Date before,
                         @Param("banned") Boolean banned,
                         @Param("minExperience") Integer minExperience,
                         @Param("maxExperience") Integer maxExperience,
                         @Param("minLevel") Integer minLevel,
                         @Param("maxLevel") Integer maxLevel,
                         Pageable pageable
    );
}
