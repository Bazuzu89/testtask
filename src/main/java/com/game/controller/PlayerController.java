package com.game.controller;

import com.game.entity.*;
import com.game.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/rest/players")
    ResponseEntity<List<Player>> getPlayers(@RequestParam(value = "name", defaultValue = "") String name,
                                            @RequestParam(value = "title", defaultValue = "") String title,
                                            @RequestParam(value = "race", defaultValue = "") Race race,
                                            @RequestParam(value = "profession", defaultValue = "") Profession profession,
                                            @RequestParam(value = "after", defaultValue = "") Long after,
                                            @RequestParam(value = "before", defaultValue = "") Long before,
                                            @RequestParam(value = "banned", defaultValue = "") Boolean banned,
                                            @RequestParam(value = "minExperience", defaultValue = "") Integer minExperience,
                                            @RequestParam(value = "maxExperience", defaultValue = "") Integer maxExperience,
                                            @RequestParam(value = "minLevel", defaultValue = "") Integer minLevel,
                                            @RequestParam(value = "maxLevel", defaultValue = "") Integer maxLevel,
                                            @RequestParam(value = "order", defaultValue = "") PlayerOrder playerOrder,
                                            @RequestParam(value = "pageNumber", defaultValue = "") Integer pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "") Integer pageSize
                                            ) {
        Page<Player> playerList = playerService.getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, playerOrder, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(playerList.getContent());
    }

    @GetMapping("/rest/players/count")
    ResponseEntity<Integer> getPlayersCount(HttpServletRequest request, HttpServletResponse  response) {
        Integer count = playerService.getCount(request);
        //TODO wrap playerList in ResponseEntity
        return null;
    }

    @PostMapping("/rest/players")
    ResponseEntity<Player> createPlayer(@RequestParam Player player) {
        Player playerCreated = playerService.create(player);
        ResponseEntity<Player> response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerCreated);
        return response;
    }

    @GetMapping("/rest/players/{id}")
    ResponseEntity<Player> getPlayerById(@RequestParam Long id) {
        Player playerFound = playerService.get(id);
        ResponseEntity<Player> response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerFound);
        return response;
    }

    @PostMapping("/rest/players/{id}")
    ResponseEntity<Player> updatePlayer(@RequestParam Long id) {
        Player playerUpdated = playerService.update(id);
        //TODO wrap playerList in ResponseEntity
        return null;
    }

    @DeleteMapping("/rest/players/{id}")
    ResponseEntity deletePlayer(@RequestParam Long id) {
        playerService.delete(id);
        return null;
    }
}
