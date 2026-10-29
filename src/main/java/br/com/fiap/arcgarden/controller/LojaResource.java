package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.loja.LojaMapper;
import br.com.fiap.arcgarden.controller.dto.loja.LojaRequestDto;
import br.com.fiap.arcgarden.controller.dto.loja.LojaResponseDto;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.Loja;
import br.com.fiap.arcgarden.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/lojas")
public class LojaResource
{
    private final LojaService service;
    public LojaResource(LojaService service) { this.service = service; }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody LojaRequestDto dto)
    {
        try {
            Loja salvo = service.criar(LojaMapper.toModel(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(LojaMapper.toResponseDto(salvo));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/lojas"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar()
    {
        try {
            List<LojaResponseDto> response = service.listarTodos().stream().map(LojaMapper::toResponseDto).toList();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/lojas"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(LojaMapper.toResponseDto(service.buscarPorId(id)));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/lojas/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody LojaRequestDto dto)
    {
        try {
            service.atualizar(id, LojaMapper.toModel(id, dto));

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/lojas/{id}"
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
                    "api/v1/lojas/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }
}