package si.fri.rso.skupina1.ponudniki.services.converters;

import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudbaEntity;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudnikBean;

import javax.inject.Inject;


public class PonudbaConverter {

    @Inject
    PonudnikBean ponudnikBean;

    public Ponudba toDto(PonudbaEntity entity) {
        Ponudba dto = new Ponudba();
        dto.setPonudbaId(entity.getId());
        dto.setIme(entity.getIme());
        dto.setCena(entity.getCena());
        dto.setPonudnikId(entity.getId());
        return dto;
    }

    public PonudbaEntity toEntity(Ponudba dto) {
        PonudbaEntity entity = new PonudbaEntity();
        entity.setId(dto.getPonudbaId());
        entity.setIme(dto.getIme());
        entity.setCena(dto.getCena());
        entity.setPonudnik(new PonudnikConverter()
                                .toEntity(ponudnikBean
                                        .getPonudnik(dto.getPonudnikId())));
        return entity;
    }
}
