package com.social.eduqualis.repository;

import com.social.eduqualis.entity.Trilha;
import com.social.eduqualis.entity.enums.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrilhaRepository extends JpaRepository<Trilha, Long> {
    Trilha findByNome(String nome);

    boolean existsByNome(String nome);

    boolean existsByTema(String tema);

    boolean existsByCategoria(VideoCategory categoria);

    boolean existsByDescricao(String descricao);

    List<Trilha> findByCategoria(VideoCategory categoria);
    List<Trilha> findByTema(String tema);
}
