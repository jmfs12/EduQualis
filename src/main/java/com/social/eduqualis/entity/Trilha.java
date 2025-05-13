package com.social.eduqualis.entity;

import com.social.eduqualis.dtos.TrilhaDTO;
import com.social.eduqualis.entity.enums.VideoCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="trilhas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tema")
    private String tema;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private VideoCategory categoria;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

    public Trilha(TrilhaDTO trilhaDTO) {
        this.nome = trilhaDTO.getNome();
        this.tema = trilhaDTO.getTema();
        this.categoria = trilhaDTO.getCategoria();
        this.descricao = trilhaDTO.getDescricao();
    }

}
