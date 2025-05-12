package com.social.eduqualis.entity.enums;

import lombok.Getter;

@Getter
public enum VideoCategory {
    MATEMATICA("Matemática"),
    PORTUGUES("Português"),
    HISTÓRIA("História"),
    GEOGRAFIA("Geografia"),
    CIÊNCIAS("Ciências"),
    LINGUA_INGLESA("Língua Inglesa"),
    LINGUA_ESPANHOLA("Língua Espanhola"),
    FILOSOFIA("Filosofia"),
    SOCIOLOGIA("Sociologia"),
    ARTES("Artes"),
    EDUCACAO_FISICA("Educação Física"),
    MÚSICA("Música"),
    CIENCIA_DA_COMPUTACAO("Ciência da Computação"),
    ENGENHARIA("Engenharia");

    private final String displayName;

    VideoCategory(String displayName) {
        this.displayName = displayName;
    }
}
