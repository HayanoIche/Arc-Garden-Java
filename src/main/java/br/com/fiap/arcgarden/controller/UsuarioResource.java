package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.UsuarioMapper;
import br.com.fiap.arcgarden.controller.dto.UsuarioRequestDto;
import br.com.fiap.arcgarden.controller.dto.UsuarioResponseDto;
import br.com.fiap.arcgarden.model.Usuario;
import br.com.fiap.arcgarden.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioResource
{
    private final UsuarioService service;
    public UsuarioResource(UsuarioService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody UsuarioRequestDto requestDto)
    {
        try {
            Usuario usuario = UsuarioMapper.toModel(requestDto);
            Usuario usuarioSalvo = service.criar(usuario);
            UsuarioResponseDto responseDto = UsuarioMapper.toResponseDto(usuarioSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{usuarioId}/missoes/{missaoId}")
    public ResponseEntity<Object> concluirMissao(@PathVariable int usuarioId, @PathVariable int missaoId) {
        try {
            service.concluirMissao(usuarioId, missaoId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Missão concluída com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{usuarioId}/itens/{itemId}")
    public ResponseEntity<Object> comprarItem(@PathVariable int usuarioId,
                                              @PathVariable int itemId,
                                              @RequestParam(defaultValue = "1") int quantidade) {
        try {
            service.comprarItem(usuarioId, itemId, quantidade);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item comprado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> listar()
    {
        try {
            List<UsuarioResponseDto> dtos = service.listar().stream()
                    .map(UsuarioMapper::toResponseDto)
                    .toList();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable int id)
    {
        try {
            Usuario usuario = service.buscarPorId(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(UsuarioMapper.toResponseDto(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable int id, @RequestBody UsuarioRequestDto requestDto) {
        try {
            Usuario usuario = UsuarioMapper.toModel(requestDto);
            service.atualizar(id, usuario);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content após atualizar com sucesso
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}