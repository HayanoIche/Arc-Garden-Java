package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.*;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.service.MissaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/missoes")
public class MissaoResource {

    private final MissaoService service;
    public MissaoResource(MissaoService service) { this.service = service; }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody MissaoRequestDto dto)
    {
        try {
            Missao salvo = service.criar(MissaoMapper.toModel(dto));

            return ResponseEntity.status(HttpStatus.CREATED).body(MissaoMapper.toResponseDto(salvo));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/missoes"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar()
    {
        try {
            List<MissaoResponseDto> response = service.listarTodos().stream().map(MissaoMapper::toResponseDto).toList();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/missoes"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id)
    {
        try {
            return ResponseEntity.ok(MissaoMapper.toResponseDto(service.buscarPorId(id)));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/missoes/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody MissaoRequestDto dto)
    {
        try {
            service.atualizar(id, MissaoMapper.toModel(id, dto));

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/missoes/{id}"
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
                    "api/v1/missoes/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }
}