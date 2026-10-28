package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.*;
import br.com.fiap.arcgarden.model.Loja;
import br.com.fiap.arcgarden.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lojas")
public class LojaResource
{
    private final LojaService service;
    public LojaResource(LojaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<LojaResponseDto> response = service.listarTodos().stream()
                .map(LojaMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(LojaMapper.toResponseDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody LojaRequestDto dto) {
        Loja salvo = service.criar(LojaMapper.toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(LojaMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody LojaRequestDto dto) {
        service.atualizar(id, LojaMapper.toModel(id, dto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}