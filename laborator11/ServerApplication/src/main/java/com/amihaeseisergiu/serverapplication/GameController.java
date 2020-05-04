package com.amihaeseisergiu.serverapplication;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

    private static final List<Game> games = new ArrayList<>();

    public GameController() {

    }

    @GetMapping
    public static synchronized List<Game> getGames() {
        return games;
    }

    @GetMapping("/count")
    public static synchronized int countGames() {
        return games.size();
    }

    @GetMapping("/{id}")
    public static synchronized Game getGame(@PathVariable("id") int id) {
        return games.stream()
                .filter(g -> g.getId() == id).findFirst()
                .orElseThrow(() -> new CustomException("Game not found"));
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public static synchronized ResponseEntity<String> createGame(@RequestBody Game game) {
        games.add(game);
        return new ResponseEntity<>("Game created successfully", HttpStatus.CREATED);
    }

    public static synchronized Game findById(int id) {
        for(Game g : games)
        {
            if(g.getId() == id) return g;
        }
        return null;
    }
}
