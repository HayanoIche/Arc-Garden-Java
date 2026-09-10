DROP TABLE tb_usuarios_missoes;
DROP TABLE tb_usuarios_loja;
DROP TABLE tb_loja;
DROP TABLE tb_missoes;
DROP TABLE tb_plantas;
DROP TABLE tb_tipo_plantas;
DROP TABLE tb_jardins;
DROP TABLE tb_usuarios;

CREATE TABLE tb_usuarios (
    usuario_id      INTEGER         GENERATED ALWAYS AS IDENTITY,
    nome            VARCHAR2(120)   NOT NULL,
    cpf             VARCHAR2(11)    NOT NULL,
    arc_score       INTEGER         NOT NULL,
    status          VARCHAR2(20)    NOT NULL,

    CONSTRAINT tb_usuarios_pk PRIMARY KEY (usuario_id),
    CONSTRAINT tb_usuarios_un UNIQUE (cpf),

    CONSTRAINT tb_usuarios_ck
        CHECK (status IN ('ATIVO', 'INATIVO', 'DESATIVADO'))
);

CREATE TABLE tb_jardins (
    jardim_id       INTEGER         GENERATED ALWAYS AS IDENTITY,
    usuario_id      INTEGER         NOT NULL,
    nome            VARCHAR2(120)   NOT NULL,
    tema            VARCHAR2(20)    NOT NULL,

    CONSTRAINT tb_jardins_pk PRIMARY KEY (jardim_id),
    CONSTRAINT tb_jardins_fk
        FOREIGN KEY (usuario_id)
        REFERENCES tb_usuarios (usuario_id),

    CONSTRAINT tb_jardins_ck
        CHECK (tema IN ('AGUA', 'ENERGIA', 'RENOVACAO', 'NATUREZA'))
);

CREATE TABLE tb_tipo_plantas (
    tipo_id         INTEGER         GENERATED ALWAYS AS IDENTITY,
    nome            VARCHAR2(120)   NOT NULL,
    nivel_inicial   VARCHAR2(1)     NOT NULL,
    nivel_maximo    VARCHAR2(1)     NOT NULL,
    categoria       VARCHAR2(20)    NOT NULL,
    xp_maximo       INTEGER         NOT NULL,
    descricao       VARCHAR2(500)   NOT NULL,

    CONSTRAINT tb_tipo_plantas_pk PRIMARY KEY (tipo_id),

    CONSTRAINT tb_tipo_plantas_ck
        CHECK (
            nivel_inicial IN ('1', '2', '3', '4', '5')
            AND nivel_maximo IN ('1', '2', '3', '4', '5')
            AND categoria IN ('AGUA', 'ENERGIA', 'RENOVACAO', 'NATUREZA')
            AND xp_maximo > 0
        )
);

CREATE TABLE tb_plantas (
    planta_id       INTEGER         GENERATED ALWAYS AS IDENTITY,
    jardim_id       INTEGER         NOT NULL,
    tipo_id         INTEGER         NOT NULL,
    raridade        VARCHAR2(20)    NOT NULL,
    level_planta    VARCHAR2(1)     NOT NULL,
    premiada        VARCHAR2(1)     NOT NULL,
    xp_atual        INTEGER         NOT NULL,

    CONSTRAINT tb_plantas_pk PRIMARY KEY (planta_id),

    CONSTRAINT tb_plantas_jardim_fk
        FOREIGN KEY (jardim_id)
        REFERENCES tb_jardins (jardim_id),

    CONSTRAINT tb_plantas_tipo_fk
        FOREIGN KEY (tipo_id)
        REFERENCES tb_tipo_plantas (tipo_id),

    CONSTRAINT tb_plantas_ck
        CHECK (
            raridade IN ('COMUM', 'RARA', 'ULTRARARA', 'BRILHANTE')
            AND level_planta IN ('1', '2', '3', '4', '5')
            AND premiada IN ('N', 'S')
            AND xp_atual >= 0
        )
);

CREATE TABLE tb_missoes (
    missao_id           INTEGER         GENERATED ALWAYS AS IDENTITY,
    nome                VARCHAR2(200)   NOT NULL,
    descricao           VARCHAR2(500)   NOT NULL,
    dificuldade         VARCHAR2(1)     NOT NULL,
    vezes               INTEGER         NOT NULL,
    recompensa_pontos   INTEGER         NOT NULL,

    CONSTRAINT tb_missoes_pk PRIMARY KEY (missao_id),

    CONSTRAINT tb_missoes_ck
        CHECK (
            dificuldade IN ('F', 'M', 'D')
            AND vezes > 0
            AND recompensa_pontos >= 0
            AND recompensa_pontos <= 100
        )
);

CREATE TABLE tb_loja (
    loja_id         INTEGER         GENERATED ALWAYS AS IDENTITY,
    nome            VARCHAR2(120)   NOT NULL,
    tipo            VARCHAR2(20)    NOT NULL,
    preco_agua      NUMERIC(5,2)    NOT NULL,

    CONSTRAINT tb_loja_pk PRIMARY KEY (loja_id),

    CONSTRAINT tb_loja_ck
        CHECK (
            tipo IN ('COSMETICO', 'MELHORIA', 'ROLETA', 'SEMENTE')
            AND preco_agua >= 0
        )
);

CREATE TABLE tb_usuarios_loja (
    usuario_loja_id     INTEGER     GENERATED ALWAYS AS IDENTITY,
    data_compra         DATE        NOT NULL,
    quantidade          INTEGER     NOT NULL,
    usuario_id          INTEGER     NOT NULL,
    loja_id             INTEGER     NOT NULL,

    CONSTRAINT tb_usuarios_loja_pk PRIMARY KEY (usuario_loja_id),

    CONSTRAINT tb_usuarios_loja_usuario_fk
        FOREIGN KEY (usuario_id)
        REFERENCES tb_usuarios (usuario_id),

    CONSTRAINT tb_usuarios_loja_loja_fk
        FOREIGN KEY (loja_id)
        REFERENCES tb_loja (loja_id),

    CONSTRAINT tb_usuarios_loja_ck
        CHECK (quantidade > 0)
);

CREATE TABLE tb_usuarios_missoes (
    usuario_missao_id   INTEGER     GENERATED ALWAYS AS IDENTITY,
    missao_id           INTEGER     NOT NULL,
    usuario_id          INTEGER     NOT NULL,
    data_conclusao      DATE        NOT NULL,
    pontos_ganhos       INTEGER     NOT NULL,

    CONSTRAINT tb_usuarios_missoes_pk PRIMARY KEY (usuario_missao_id),

    CONSTRAINT tb_usuarios_missoes_missoes_fk
        FOREIGN KEY (missao_id)
        REFERENCES tb_missoes (missao_id),

    CONSTRAINT tb_usuario_missoes_usuario_fk
        FOREIGN KEY (usuario_id)
        REFERENCES tb_usuarios (usuario_id),

    CONSTRAINT tb_usuarios_missoes_ck
        CHECK (
            pontos_ganhos >= 0
            AND pontos_ganhos <= 100
        )
);

ALTER TABLE tb_usuarios
    ADD CONSTRAINT tb_usuarios_score_ck
    CHECK (arc_score >= 0 AND soul_points >= 0);
    
