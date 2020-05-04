package com.amihaeseisergiu.serverapplication;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private static final List<Player> players = new ArrayList<>();

    public PlayerController() {

    }

    @GetMapping
    public static synchronized List<Player> getPlayers() {
        return players;
    }

    @GetMapping("/count")
    public static synchronized int countPlayers() {
        return players.size();
    }

    @GetMapping("/{id}")
    public static synchronized Player getPlayer(@PathVariable("id") int id) {
        return players.stream()
                .filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new CustomException("Player not found"));
    }

    @PostMapping
    public static synchronized int createPlayer(@RequestParam String name) {
        int id = 1 + players.size();
        players.add(new Player(id, name));
        return id;
    }

    @PostMapping(value = "/obj", consumes = "application/json")
    public static synchronized ResponseEntity<String> createPlayer(@RequestBody Player player) {
        players.add(player);
        return new ResponseEntity<>("Player created successfully", HttpStatus.CREATED);
    }

    public static synchronized Player findById(int id) {
        for(Player p : players)
        {
            if(p.getId() == id) return p;
        }
        return null;
    }

    @PutMapping("/{id}")
    public static synchronized ResponseEntity<String> updatePlayer(@PathVariable int id, @RequestParam String name) {
        Player player = findById(id);
        if (player == null) {
            throw new CustomException("Player not found");
        }
        player.setName(name);
        return new ResponseEntity<>("Player updated successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public static synchronized ResponseEntity<String> deletePlayer(@PathVariable int id) {
        Player player = findById(id);
        if (player == null) {
            throw new CustomException("Player not found");
        }
        players.remove(player);
        return new ResponseEntity<>("Player removed", HttpStatus.OK);
    }

}
