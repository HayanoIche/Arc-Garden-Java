package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.usuario.UsuarioMapper;
import br.com.fiap.arcgarden.controller.dto.usuario.UsuarioRequestDto;
import br.com.fiap.arcgarden.controller.dto.usuario.UsuarioResponseDto;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.Usuario;
import br.com.fiap.arcgarden.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioResource
{
    private final UsuarioService service;
    public UsuarioResource(UsuarioService service) { this.service = service; }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioRequestDto requestDto)
    {
        try {
            Usuario usuario = UsuarioMapper.toModel(requestDto);
            Usuario usuarioSalvo = service.criar(usuario);
            UsuarioResponseDto responseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/usuarios"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PostMapping("/{usuarioId}/missoes/{missaoId}")
    public ResponseEntity<?> concluirMissao(@PathVariable int usuarioId, @PathVariable int missaoId)
    {
        try {
            service.concluirMissao(usuarioId, missaoId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Missão concluída com sucesso!");
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/usuarios/{usuarioId}/missoes/{missaoId}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PostMapping("/{usuarioId}/itens/{itemId}")
    public ResponseEntity<?> comprarItem(@PathVariable int usuarioId, @PathVariable int itemId, @RequestParam(defaultValue = "1") int quantidade)
    {
        try {
            service.comprarItem(usuarioId, itemId, quantidade);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item comprado com sucesso!");
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/usuarios/{usuarioId}/itens/{itemId}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar()
    {
        try {
            List<UsuarioResponseDto> dtos = service.listar().stream().map(UsuarioMapper::toResponseDto).toList();
            return ResponseEntity.ok(dtos);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/usuarios"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id)
    {
        try {
            Usuario usuario = service.buscarPorId(id);

            if (usuario == null) { throw new RuntimeException("Usuário não encontrado"); }
            return ResponseEntity.ok(UsuarioMapper.toResponseDto(usuario));
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


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody UsuarioRequestDto requestDto) {
        try {
            Usuario usuario = UsuarioMapper.toModel(requestDto);
            service.atualizar(id, usuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

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