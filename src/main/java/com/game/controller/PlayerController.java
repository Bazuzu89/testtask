package com.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.game.entity.*;
import com.game.exception.InvalidInputException;
import com.game.exception.InvalidPlayerParametersException;
import com.game.exception.PlayerNotFoundException;
import com.game.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/rest/players", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createPlayer(@RequestBody Player player) {

        ResponseEntity<Player> response = null;
        try {
            Player playerCreated = playerService.create(player);
            response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerCreated);
        } catch (InvalidPlayerParametersException | ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/rest/players/{id}")
    ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player playerFound;
        try {
            playerFound =  playerService.get(id);
            ResponseEntity<Player> response = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerFound);
            return response;
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/rest/players/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updatePlayer(@RequestBody Player player, @PathVariable Long id, HttpServletRequest request, HttpServletResponse resp) {
        Player playerUpdated;
            try {
                playerUpdated = playerService.update(id, player);
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(playerUpdated);
            } catch (InvalidInputException | ParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (PlayerNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @DeleteMapping("/rest/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        try {
            playerService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PlayerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
