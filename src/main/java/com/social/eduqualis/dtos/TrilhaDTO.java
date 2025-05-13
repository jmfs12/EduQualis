package com.social.eduqualis.dtos;

import com.social.eduqualis.entity.enums.VideoCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrilhaDTO {

    private String nome;
    private String tema;
    private VideoCategory categoria;
    private String descricao;
    private List<VideoDTO> videos;

    public TrilhaDTO(String nome, String tema, VideoCategory categoria, String descricao) {

        this.nome = nome;
        this.tema = tema;
        this.categoria = categoria;
        this.descricao = descricao;
        this.videos = new ArrayList<>();
    }

}
