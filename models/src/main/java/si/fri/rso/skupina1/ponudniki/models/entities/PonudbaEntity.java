package si.fri.rso.skupina1.ponudniki.models.entities;

import javax.persistence.*;


@Entity
@Table(name = "ponudbe")
@NamedQueries(value =
        {
                @NamedQuery(name = "PonudbaEntity.getAll",
                        query = "SELECT p FROM PonudbaEntity p")
        }
)
public class PonudbaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ponudnikId")
    private PonudnikEntity ponudnik;

    @Column(name = "ime")
    private String ime;

    @Column(name = "cena")
    private Float cena;


    public PonudbaEntity() {
        super();
    }

    public PonudbaEntity(String ime, PonudnikEntity ponudnik, Float cena) {
        super();
        this.ime = ime;
        this.ponudnik = ponudnik;
        this.cena = cena;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PonudnikEntity getPonudnik() {
        return ponudnik;
    }

    public void setPonudnik(PonudnikEntity ponudnik) {
        this.ponudnik = ponudnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }
}
