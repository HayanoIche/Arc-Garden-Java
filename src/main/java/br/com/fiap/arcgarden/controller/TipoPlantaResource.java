package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.*;
import br.com.fiap.arcgarden.model.TipoPlanta;
import br.com.fiap.arcgarden.service.TipoPlantaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-plantas")
@CrossOrigin(origins = "*")
public class TipoPlantaResource
{
    private final TipoPlantaService service;

    public TipoPlantaResource(TipoPlantaService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoPlantaResponseDto>> listar(@RequestParam(required = false) String categoria) {
        List<TipoPlanta> lista = (categoria != null && !categoria.trim().isEmpty()) ?
                service.buscarPorCategoria(categoria) : service.listarTodos();

        List<TipoPlantaResponseDto> response = lista.stream()
                .map(TipoPlantaMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPlantaResponseDto> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(TipoPlantaMapper.toResponseDto(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<TipoPlantaResponseDto> criar(@RequestBody TipoPlantaRequestDto dto) {
        TipoPlanta salvo = service.criar(TipoPlantaMapper.toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(TipoPlantaMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody TipoPlantaRequestDto dto) {
        service.atualizar(id, TipoPlantaMapper.toModel(id, dto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}