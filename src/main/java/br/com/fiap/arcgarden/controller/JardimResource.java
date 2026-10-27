package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.JardimRequestDto;
import br.com.fiap.arcgarden.controller.dto.JardimResponseDto;
import br.com.fiap.arcgarden.controller.dto.JardimMapper;
import br.com.fiap.arcgarden.controller.dto.PlantaRequestDto;
import br.com.fiap.arcgarden.model.Jardim;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.TipoPlanta;
import br.com.fiap.arcgarden.service.JardimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jardins")
public class JardimResource
{
    private final JardimService service;
    public JardimResource(JardimService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<JardimResponseDto>> listar(
            @RequestParam(required = false) String tema,
            @RequestParam(required = false) Integer usuarioId) {

        List<Jardim> jardins;
        if (tema != null && !tema.trim().isEmpty()) {
            jardins = service.buscarPorTema(tema);
        } else if (usuarioId != null) {
            Jardim jardim = service.buscarPorUsuarioId(usuarioId);
            jardins = jardim != null ? List.of(jardim) : List.of();
        } else {
            jardins = service.listarTodos();
        }

        List<JardimResponseDto> response = jardins.stream()
                .map(JardimMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JardimResponseDto> buscarPorId(@PathVariable int id) {
        Jardim jardim = service.buscarPorId(id);
        return ResponseEntity.ok(JardimMapper.toResponseDto(jardim));
    }

    @PostMapping
    public ResponseEntity<JardimResponseDto> criar(@RequestBody JardimRequestDto dto) {
        Jardim jardim = JardimMapper.toModel(dto);
        Jardim salvo = service.criar(jardim);
        return ResponseEntity.status(HttpStatus.CREATED).body(JardimMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody JardimRequestDto dto) {
        Jardim jardim = JardimMapper.toModel(id, dto);
        service.atualizar(id, jardim);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/plantas")
    public ResponseEntity<Void> adicionarPlanta(@PathVariable int id, @RequestBody PlantaRequestDto dto) {
        TipoPlanta tipo = new TipoPlanta();
        tipo.setId(dto.tipoId());

        Planta planta = new Planta();
        planta.setJardimId(id);
        planta.setTipoPlanta(tipo);
        planta.setRaridade(dto.raridade());
        planta.setNivelPlanta(dto.nivelPlanta());
        planta.setPremiada(dto.premiada());
        planta.setXpAtual(dto.xpAtual());

        service.adicionarPlanta(id, planta);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}