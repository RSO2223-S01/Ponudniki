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
import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.services.converters.PonudbaConverter;
import si.fri.rso.skupina1.ponudniki.models.entities.PonudbaEntity;


@RequestScoped
public class PonudbaBean {

    private Logger log = Logger.getLogger(PonudbaBean.class.getName());

    @Inject
    private EntityManager em;


    public List<Ponudba> getPonudbe() {

        TypedQuery<PonudbaEntity> query = em.createNamedQuery(
                "PonudbaEntity.getAll", PonudbaEntity.class);

        List<PonudbaEntity> resultList = query.getResultList();

        return resultList.stream().map(PonudbaConverter::toDto).collect(Collectors.toList());

    }

    public List<Ponudba> getPonudbeFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, PonudbaEntity.class, queryParameters).stream()
                .map(PonudbaConverter::toDto).collect(Collectors.toList());
    }

    public Ponudba getPonudba(Integer id) {

        PonudbaEntity ponudbaEntity = em.find(PonudbaEntity.class, id);

        if (ponudbaEntity == null) {
            throw new NotFoundException();
        }

        Ponudba ponudba = PonudbaConverter.toDto(ponudbaEntity);
        return ponudba;
    }

    public Ponudba createPonudba(Ponudba ponudba) {


        PonudbaEntity ponudbaEntity = PonudbaConverter.toEntity(ponudba);

        try {
            beginTx();
            em.persist(ponudbaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (ponudbaEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return PonudbaConverter.toDto(ponudbaEntity);
    }

    public Ponudba putPonudba(Integer id, Ponudba ponudba) {

        PonudbaEntity c = em.find(PonudbaEntity.class, id);

        if (c == null) {
            return null;
        }

        PonudbaEntity updatedPonudbaEntity = PonudbaConverter.toEntity(ponudba);

        try {
            beginTx();
            updatedPonudbaEntity.setId(c.getId());
            updatedPonudbaEntity = em.merge(updatedPonudbaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return PonudbaConverter.toDto(updatedPonudbaEntity);
    }

    public boolean deletePonudba(Integer id) {

        PonudbaEntity ponudba = em.find(PonudbaEntity.class, id);

        if (ponudba != null) {
            try {
                beginTx();
                em.remove(ponudba);
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
