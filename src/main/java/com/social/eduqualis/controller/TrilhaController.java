package com.social.eduqualis.controller;

import com.social.eduqualis.dtos.TrilhaDTO;
import com.social.eduqualis.entity.Trilha;
import com.social.eduqualis.service.TrilhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trilha")
public class TrilhaController {
    private final TrilhaService trilhaService;

    @Autowired
    public TrilhaController(TrilhaService trilhaService) {
        this.trilhaService = trilhaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTrilha(@RequestBody TrilhaDTO trilhaDTO) {
        trilhaService.createTrilha(trilhaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TrilhaDTO>> getAllTrilhas() {
        return ResponseEntity.status(HttpStatus.OK).body(trilhaService.getALlTrilha());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TrilhaDTO> getTrilhaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(trilhaService.getTrilhaById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTrilha(@PathVariable Long id ,@RequestBody TrilhaDTO trilhaDTO) {
        trilhaService.updateTrilha(id, trilhaDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteTrilha(@PathVariable Long id) {
        trilhaService.deleteTrilha(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
