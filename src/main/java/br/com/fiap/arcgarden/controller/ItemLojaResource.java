package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.ItemLojaRequestDto;
import br.com.fiap.arcgarden.controller.dto.ItemLojaResponseDto;
import br.com.fiap.arcgarden.controller.dto.ItemLojaMapper;
import br.com.fiap.arcgarden.exceptions.ErrorResponse;
import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.service.ItemLojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/itens-loja")
public class ItemLojaResource
{
    private final ItemLojaService service;
    public ItemLojaResource(ItemLojaService service) { this.service = service; }


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ItemLojaRequestDto dto)
    {
        try {
            ItemLoja item = ItemLojaMapper.toModel(dto);
            ItemLoja salvo = service.criar(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(ItemLojaMapper.toResponseDto(salvo));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/itens-loja"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) String tipo)
    {
        try {
            List<ItemLoja> itens = (tipo != null && !tipo.trim().isEmpty())
                    ? service.buscarPorTipo(tipo)
                    : service.listarTodos();

            List<ItemLojaResponseDto> response = itens.stream()
                    .map(ItemLojaMapper::toResponseDto)
                    .toList();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/itens-loja"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id)
    {
        try {
            ItemLoja item = service.buscarPorId(id);
            return ResponseEntity.ok(ItemLojaMapper.toResponseDto(item));
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/itens-loja/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody ItemLojaRequestDto dto)
    {
        try {
            ItemLoja item = ItemLojaMapper.toModel(id, dto);
            service.atualizar(id, item);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            ErrorResponse error = new ErrorResponse(
                    Instant.now(),
                    status.value(),
                    e.getMessage(),
                    "api/v1/itens-loja/{id}"
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
                    "api/v1/itens-loja/{id}"
            );

            return ResponseEntity.status(status).body(error);
        }
    }
}