package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Recurso_Linea_Base")
public class BaseLineResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor_monetario")
    private Double money;

    @Size(max = 1024, message = "The content is too long.")
    @Column(name = "descripcion_recurso", columnDefinition = "text")
    private String detail;

    @Column(name = "user_id")
    private Long user;

    @ManyToOne
    @JoinColumn(name="linea_base_id", nullable=false)
    private BaseLine baseLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public BaseLine getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(BaseLine baseLine) {
        this.baseLine = baseLine;
    }
}
