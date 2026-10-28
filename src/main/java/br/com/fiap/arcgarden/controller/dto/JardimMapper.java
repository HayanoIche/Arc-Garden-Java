package br.com.fiap.arcgarden.controller.dto;

import br.com.fiap.arcgarden.model.Jardim;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.TipoPlanta;

import java.util.List;

public class JardimMapper {

    public static Jardim toModel(JardimRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Jardim jardim = new Jardim();
        jardim.setNome(dto.nome());
        jardim.setTema(dto.tema());
        jardim.setUsuarioId(dto.usuarioId());
        return jardim;
    }

    public static Jardim toModel(int id, JardimRequestDto dto) {
        Jardim jardim = toModel(dto);
        if (jardim != null) {
            jardim.setId(id);
        }
        return jardim;
    }

    public static JardimResponseDto toResponseDto(Jardim jardim) {
        if (jardim == null) {
            return null;
        }

        List<PlantaResponseDto> plantasDto = null;
        if (jardim.getPlantas() != null) {
            plantasDto = jardim.getPlantas().stream().map(JardimMapper::toPlantaResponseDto).toList();
        }

        return new JardimResponseDto(
                jardim.getId(),
                jardim.getNome(),
                jardim.getTema(),
                jardim.getUsuarioId(),
                plantasDto
        );
    }

    private static PlantaResponseDto toPlantaResponseDto(Planta planta) {
        TipoPlantaResponseDto tipoDto = null;
        if (planta.getTipoPlanta() != null) {
            TipoPlanta t = planta.getTipoPlanta();
            tipoDto = new TipoPlantaResponseDto(
                    t.getId(),
                    t.getNome(),
                    t.getCategoria(),
                    t.getXpMaximo(),
                    t.getDescricao()
            );
        }

        return new PlantaResponseDto(
                planta.getId(),
                planta.getRaridade(),
                planta.getNivelPlanta(),
                planta.getPremiada(),
                planta.getXpAtual(),
                tipoDto
        );
    }
}
