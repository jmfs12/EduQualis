package com.social.eduqualis.service;

import com.social.eduqualis.dtos.TrilhaDTO;
import com.social.eduqualis.dtos.UserDTO;
import com.social.eduqualis.dtos.VideoDTO;
import com.social.eduqualis.entity.Trilha;
import com.social.eduqualis.exceptions.ObjectAlreadyExistsException;
import com.social.eduqualis.exceptions.ObjectNotFoundException;
import com.social.eduqualis.repository.TrilhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrilhaService {
    TrilhaRepository tRepo;

    @Autowired
    public TrilhaService(TrilhaRepository tRepo) {
        this.tRepo = tRepo;
    }

    public void createTrilha(TrilhaDTO trilhaDTO) {

        if (tRepo.existsByNome(trilhaDTO.getNome())) {
            throw new ObjectAlreadyExistsException("Trilha with this name already exists");
        }

        Trilha trilha = new Trilha(trilhaDTO);
        tRepo.save(trilha);

    }

    public List<TrilhaDTO> getALlTrilha() {
        List<Trilha> trilhas = tRepo.findAll();
        List<TrilhaDTO> trilhaDTOs = new ArrayList<>();

        for (Trilha trilha : trilhas) {
            TrilhaDTO dto = new TrilhaDTO(trilha.getNome(), trilha.getTema(), trilha.getCategoria(), trilha.getDescricao());
            trilhaDTOs.add(dto);
        }

        return trilhaDTOs;
    }

    public TrilhaDTO getTrilhaById(Long id) {
        Trilha trilha = tRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trilha not found"));
        TrilhaDTO trilhaDTO = new TrilhaDTO(trilha.getNome(), trilha.getTema(), trilha.getCategoria(), trilha.getDescricao());
        trilhaDTO.setVideos(trilha.getVideos().stream()
                .map(video -> new VideoDTO(
                        new UserDTO(video.getUser()),
                        video.getCategory(),
                        video.getDescription(),
                        video.getVideoPath(),
                        video.getCreatedAt(),
                        video.getVideoName()))
                .collect(Collectors.toList()));
        return trilhaDTO;
    }

    public void updateTrilha(Long id, TrilhaDTO trilhaDTO) {
        Trilha trilha = tRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trilha not found"));
        trilha.setNome(trilhaDTO.getNome());
        trilha.setTema(trilhaDTO.getTema());
        trilha.setCategoria(trilhaDTO.getCategoria());
        trilha.setDescricao(trilhaDTO.getDescricao());
        tRepo.save(trilha);
    }

    public void deleteTrilha(Long id) {
        Trilha trilha = tRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trilha not found"));
        tRepo.delete(trilha);
    }
}
