package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.JardimRequestDto;
import br.com.fiap.arcgarden.controller.dto.JardimResponseDto;
import br.com.fiap.arcgarden.controller.dto.JardimMapper;
import br.com.fiap.arcgarden.controller.dto.PlantaRequestDto;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.Jardim;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.TipoPlanta;
import br.com.fiap.arcgarden.service.JardimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/jardins")
public class JardimResource
{
    private final JardimService service;
    public JardimResource(JardimService service) { this.service = service; }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody JardimRequestDto dto)
    {
        try {
            Jardim jardim = JardimMapper.toModel(dto);
            Jardim salvo = service.criar(jardim);

            return ResponseEntity.status(HttpStatus.CREATED).body(JardimMapper.toResponseDto(salvo));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PostMapping("/{id}/plantas")
    public ResponseEntity<?> adicionarPlanta(@PathVariable int id, @RequestBody PlantaRequestDto dto) {
        try {
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
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins/{id}/plantas"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) String tema, @RequestParam(required = false) Integer usuarioId)
    {
        try {
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
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id)
    {
        try {
            Jardim jardim = service.buscarPorId(id);
            return ResponseEntity.ok(JardimMapper.toResponseDto(jardim));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody JardimRequestDto dto)
    {
        try {
            Jardim jardim = JardimMapper.toModel(id, dto);
            service.atualizar(id, jardim);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id)
    {
        try {
            service.deletar(id);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/jardins/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }
}