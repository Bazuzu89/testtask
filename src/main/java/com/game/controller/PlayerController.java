package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/rest/players")
    ResponseEntity<Player[]> getPlayers(HttpServletRequest request, HttpServletResponse  response) {
        Player[] playerList = playerService.getPlayers(request);
        //TODO wrap playerList in ResponseEntity
        return null;
    }

    @GetMapping("/rest/players/count")
    ResponseEntity<Integer> getPlayersCount(HttpServletRequest request, HttpServletResponse  response) {
        Integer count = playerService.getCount(request);
        //TODO wrap playerList in ResponseEntity
        return null;
    }

    @PostMapping("/rest/players")
    ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player playerCreated = playerService.create(player);
        ResponseEntity<Player> response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerCreated);
        return response;
    }

    @GetMapping("/rest/players/{id}")
    ResponseEntity<Player> getPlayerById(@RequestParam Long id) {
        Player playerFound = playerService.get(id);
        //TODO wrap playerList in ResponseEntity
        return null;
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
