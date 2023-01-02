package si.fri.rso.skupina1.ponudniki.services.converters;

import si.fri.rso.skupina1.ponudniki.lib.Ponudnik;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudnikEntity;

import java.util.stream.Collectors;

public class PonudnikConverter {
    public static Ponudnik toDto(PonudnikEntity entity) {
        Ponudnik dto = new Ponudnik();
        dto.setPonudnikId(entity.getId());
        dto.setIme(entity.getIme());
        dto.setMesto(entity.getMesto());
        dto.setPostnaSt(entity.getPostnaSt());
        dto.setUlica(entity.getUlica());
        dto.setPonudbe(entity.getPonudbe().stream().map(PonudbaConverter::toDto).collect(Collectors.toList()));

        return dto;
    }

    public static PonudnikEntity toEntity(Ponudnik dto) {
        PonudnikEntity entity = new PonudnikEntity();
        entity.setId(dto.getPonudnikId());
        entity.setIme(dto.getIme());
        entity.setMesto(dto.getMesto());
        entity.setPostnaSt(dto.getPostnaSt());
        entity.setUlica(dto.getUlica());
        entity.setPonudbe(dto.getPonudbe().stream().map(PonudbaConverter::toEntity).collect(Collectors.toList()));

        return entity;
    }

}