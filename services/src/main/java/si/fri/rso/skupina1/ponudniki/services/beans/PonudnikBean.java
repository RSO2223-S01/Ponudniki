package si.fri.rso.skupina1.ponudniki.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.lib.Ponudnik;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudbaEntity;
import si.fri.rso.skupina1.ponudniki.services.converters.PonudbaConverter;
import si.fri.rso.skupina1.ponudniki.services.converters.PonudnikConverter;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudnikEntity;



@RequestScoped
public class PonudnikBean {

    private Logger log = Logger.getLogger(PonudnikBean.class.getName());

    @Inject
    private EntityManager em;

    @Timed
    @Metered(name = "getPonudniki_rate")
    public List<Ponudnik> getPonudniki() {

        TypedQuery<PonudnikEntity> query = em.createNamedQuery(
                "PonudnikEntity.getAll", PonudnikEntity.class);

        List<PonudnikEntity> resultList = query.getResultList();

        return resultList.stream().map(new PonudnikConverter()::toDto).collect(Collectors.toList());

    }

    @Timed
    @Metered(name = "getPonudnikiFilter_rate")
    public List<Ponudnik> getPonudnikiFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, PonudnikEntity.class, queryParameters).stream()
                .map(new PonudnikConverter()::toDto).collect(Collectors.toList());
    }

    @Timed
    @Metered(name = "getPonudnik_rate")
    public Ponudnik getPonudnik(Integer id) {

        PonudnikEntity ponudnikEntity = em.find(PonudnikEntity.class, id);

        if (ponudnikEntity == null) {
            throw new NotFoundException();
        }

        Ponudnik ponudnik = new PonudnikConverter().toDto(ponudnikEntity);
        return ponudnik;
    }

    @Timed
    @Metered(name = "createPonudnik_rate")
    public Ponudnik createPonudnik(Ponudnik ponudnik) {

        PonudnikConverter ponudnikConverter = new PonudnikConverter();
        PonudnikEntity ponudnikEntity = ponudnikConverter.toEntity(ponudnik);

        try {
            beginTx();
            em.persist(ponudnikEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (ponudnikEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ponudnikConverter.toDto(ponudnikEntity);
    }

    @Timed
    @Metered(name = "putPonudnik_rate")
    public Ponudnik putPonudnik(Integer id, Ponudnik ponudnik) {

        PonudnikEntity c = em.find(PonudnikEntity.class, id);
        PonudnikConverter ponudnikConverter = new PonudnikConverter();

        if (c == null) {
            return null;
        }

        PonudnikEntity updatedPonudnikEntity = ponudnikConverter.toEntity(ponudnik);

        try {
            beginTx();
            updatedPonudnikEntity.setId(c.getId());
            updatedPonudnikEntity = em.merge(updatedPonudnikEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ponudnikConverter.toDto(updatedPonudnikEntity);
    }

    @Timed
    @Metered(name = "deletePonudnik_rate")
    public boolean deletePonudnik(Integer id) {

        PonudnikEntity ponudnik = em.find(PonudnikEntity.class, id);

        if (ponudnik != null) {
            try {
                beginTx();
                em.remove(ponudnik);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }


    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
