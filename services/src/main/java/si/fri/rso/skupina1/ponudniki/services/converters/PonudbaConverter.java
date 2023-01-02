package si.fri.rso.skupina1.ponudniki.services.converters;

import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudbaEntity;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudnikBean;

import javax.inject.Inject;


public class PonudbaConverter {

    public static Ponudba toDto(PonudbaEntity entity) {
        Ponudba dto = new Ponudba();
        dto.setPonudbaId(entity.getId());
        dto.setIme(entity.getIme());
        dto.setCena(entity.getCena());
        return dto;
    }

    public static PonudbaEntity toEntity(Ponudba dto) {
        PonudbaEntity entity = new PonudbaEntity();
        entity.setId(dto.getPonudbaId());
        entity.setIme(dto.getIme());
        entity.setCena(dto.getCena());
        return entity;
    }
}
