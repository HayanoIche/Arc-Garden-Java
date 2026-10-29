package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.usuario.UsuarioMapper;
import br.com.fiap.arcgarden.controller.dto.usuario.UsuarioRankingResponseDto;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.Usuario;
import br.com.fiap.arcgarden.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/ranking/mensal")
public class RankingResource
{
    private final UsuarioService service;
    public RankingResource(UsuarioService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> listar()
    {
        try {
            List<Usuario> usuarios = service.listarRanking();
            List<UsuarioRankingResponseDto> dtos = new ArrayList<>();

            for (int i = 0; i < usuarios.size(); i++)
            {
                Usuario usuario = usuarios.get(i);
                int posicao = i + 1;

                UsuarioRankingResponseDto dto = UsuarioMapper.toRankingResponseDto(usuario, posicao);
                dtos.add(dto);
            }

            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/ranking/mensal"
            );

            return ResponseEntity.status(status).body(error);
        }
    }

    @GetMapping("/{posicao}")
    public ResponseEntity<?> buscarPorPosicao(@PathVariable int posicao)
    {
        try {
            Usuario usuario = service.buscarPorPosicaoRanking(posicao);
            if (usuario == null) { return ResponseEntity.notFound().build(); }

            return ResponseEntity.ok(UsuarioMapper.toRankingResponseDto(usuario, posicao));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }
}
