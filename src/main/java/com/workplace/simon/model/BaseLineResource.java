package com.workplace.simon.model;

import javax.persistence.*;

@Entity
@Table(name = "Recurso_Linea_Base")
public class BaseLineResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "valor_monetario")
    private Double money;

    @Column(name = "descripcion_recurso")
    private String detail;

    @Column(name = "user_id")
    private Long user;

//    @Column(name = "linea_base_id")
    @ManyToOne
    @JoinColumn(name="linea_base_id", nullable=false)
    private BaseLine baseLine;
}
