
# ARC-GARDEN JAVA
## GERAL
Repositório do backend (API REST) do projeto ArcGarden desenvolvido pela equipe Arceus para challenge fiap 2026.
## DOCUMENTAÇÃO
Todas as informações da documentação estão dentro do arquivo documentação.pdf na raiz do projeto.
## API
explicação rápida dos endpoints da API
### USUARIO

/api/v1/usuarios
-- pega os usuarios ou manda um usuário (GET/POST)

/api/v1/usuarios/{id usuario}
-- pega um usuário específico, ou atualiza um em específico (GET/PUT)

/api/v1/usuarios/{id usuario}/missoes/{id missao}
-- manda uma missão pro jogador (POST)

/api/v1/usuarios/{id usuario}/itens/{id item}
-- manda um item pro jogador (POST)

### JARDIM
api/v1/jardins
-- pega todos os jardins, ou cria um novo (GET/POST)

api/v1/jardins/{id}/plantas
-- adiciona uma planta nova no jardim (POST)

api/v1/jardins/{id}
-- pega, atualiza, ou deleta um jardim específico (GET/PUT/DELETE)

### RANKING
api/v1/ranking/mensal
-- pega o ranking (GET)

api/v1/ranking/mensal/{posicao}
-- pega um usuario de uma certa posição do ranking (GET)

### MISSÕES
api/v1/missoes
-- pega todas as missões ou manda uma missão (GET/POST)

api/v1/missoes/{missao id}
-- pega, atualiza, ou deleta uma missão em específico (GET/PUT/DELETE)

### TIPO-PLANTA
api/v1/tipos-plantas
-- pega os tipos-planta ou cria um tipo-planta (GET/POST)

api/v1/tipos-plantas/{id planta}
-- pega um tipo-planta específico, atualiza um em específico, ou deleta (GET/PUT/DELETE)

### LOJA
api/v1/lojas
-- pega todas as lojas ou manda uma loja nova (GET/POST)

api/v1/lojas/{loja id}
-- pega, atualiza, ou deleta uma loja em específico (GET/PUT/DELETE)

### ITEM-LOJA

api/v1/itens-loja
-- pega todos os itens, ou cadastra um item novo (GET/POST)

api/v1/itens-loja/{id}
-- pega, atualiza ou deleta um item em específico

## LINK DO RENDER
todo: colocar o link do render aqui!