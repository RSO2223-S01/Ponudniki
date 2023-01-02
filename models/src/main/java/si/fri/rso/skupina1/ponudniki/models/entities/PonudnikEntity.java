package si.fri.rso.skupina1.ponudniki.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ponudniki")
@NamedQueries(value =
        {
                @NamedQuery(name = "PonudnikEntity.getAll",
                        query = "SELECT p FROM PonudnikEntity p")
        }
)
public class PonudnikEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "mesto")
    private String mesto;

    @Column(name = "postnaSt")
    private Long postnaSt;

    @Column(name = "ulica")
    private String ulica;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PonudbaEntity> ponudbe;


    public PonudnikEntity() {
        super();
    }


    public PonudnikEntity(String ime, String mesto, Long postnaSt, String ulica) {
        super();
        this.ime = ime;
        this.mesto = mesto;
        this.postnaSt = postnaSt;
        this.ulica = ulica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public Long getPostnaSt() {
        return postnaSt;
    }

    public void setPostnaSt(Long postnaSt) {
        this.postnaSt = postnaSt;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public List<PonudbaEntity> getPonudbe() {
        return ponudbe;
    }

    public void setPonudbe(List<PonudbaEntity> ponudbe) {
        this.ponudbe = ponudbe;
    }
}
