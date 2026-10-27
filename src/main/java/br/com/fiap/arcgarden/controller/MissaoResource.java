package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.*;
import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.service.MissaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@CrossOrigin(origins = "*")
public class MissaoResource {

    private final MissaoService service;
    public MissaoResource(MissaoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<MissaoResponseDto>> listar() {
        List<MissaoResponseDto> response = service.listarTodos().stream()
                .map(MissaoMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponseDto> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(MissaoMapper.toResponseDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<MissaoResponseDto> criar(@RequestBody MissaoRequestDto dto) {
        Missao salvo = service.criar(MissaoMapper.toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(MissaoMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody MissaoRequestDto dto) {
        service.atualizar(id, MissaoMapper.toModel(id, dto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}