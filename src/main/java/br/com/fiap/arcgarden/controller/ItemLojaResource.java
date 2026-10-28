package br.com.fiap.arcgarden.controller;

import br.com.fiap.arcgarden.controller.dto.ItemLojaRequestDto;
import br.com.fiap.arcgarden.controller.dto.ItemLojaResponseDto;
import br.com.fiap.arcgarden.controller.dto.ItemLojaMapper;
import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.service.ItemLojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-loja")
public class ItemLojaResource
{
    private final ItemLojaService service;
    public ItemLojaResource(ItemLojaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) String tipo) {
        List<ItemLoja> itens = (tipo != null && !tipo.trim().isEmpty())
                ? service.buscarPorTipo(tipo)
                : service.listarTodos();

        List<ItemLojaResponseDto> response = itens.stream()
                .map(ItemLojaMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ItemLoja item = service.buscarPorId(id);
        return ResponseEntity.ok(ItemLojaMapper.toResponseDto(item));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ItemLojaRequestDto dto) {
        ItemLoja item = ItemLojaMapper.toModel(dto);
        ItemLoja salvo = service.criar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemLojaMapper.toResponseDto(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable int id, @RequestBody ItemLojaRequestDto dto) {
        ItemLoja item = ItemLojaMapper.toModel(id, dto);
        service.atualizar(id, item);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}