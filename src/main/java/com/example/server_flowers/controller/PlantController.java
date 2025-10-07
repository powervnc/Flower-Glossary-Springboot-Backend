package com.example.server_flowers.controller;

import com.example.server_flowers.PlantWebSocketHandler;
import com.example.server_flowers.model.Flower;
import com.example.server_flowers.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;
    private final PlantWebSocketHandler plantWebSocketHandler;

    @Autowired
    public PlantController(PlantService plantService, PlantWebSocketHandler plantWebSocketHandler) {
        this.plantService = plantService;
        this.plantWebSocketHandler = plantWebSocketHandler;
    }

    // Get all plants
    @GetMapping
    public ResponseEntity<List<Flower>> getAllPlants() {
        return ResponseEntity.status(HttpStatus.OK).body(plantService.getAllPlants());
    }

    // Get a specific plant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flower> getPlantById(@PathVariable String id) {
        Optional<Flower> plant = plantService.getPlantById(id);
        return plant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new plant
    @PostMapping
    public ResponseEntity<Flower> addPlant(@RequestBody Flower plant, @RequestHeader("session-id") String sessionId) {
        Flower savedPlant = plantService.addPlant(plant);
        sendMessage("CREATE", savedPlant, sessionId);
        return ResponseEntity.status(HttpStatus.OK).body(savedPlant);
    }

    // Update an existing plant
    @PutMapping
    public ResponseEntity<Flower> updatePlant(@RequestBody Flower plant, @RequestHeader("session-id") String sessionId) {
        try {
            Flower updatedPlant = plantService.updatePlant(plant);
            sendMessage("UPDATE", updatedPlant, sessionId);
            return ResponseEntity.ok(updatedPlant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a plant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable String id, @RequestHeader("session-id") String sessionId) {
        try {
            plantService.deletePlant(id);
            sendMessage("DELETE", id, sessionId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private void sendMessage(String type, Object data, String sessionId) {
        String message = createMessage(type, data, sessionId);
        plantWebSocketHandler.broadcast(message);
    }

    private String createMessage(String type, Object data, String sessionId) {
        return String.format("{\"type\":\"%s\",\"data\":\"%s\",\"senderSessionId\":\"%s\"}", type, data, sessionId);
    }
}
