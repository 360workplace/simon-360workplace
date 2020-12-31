package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Norma_Politica")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    @Column(columnDefinition = "smallint", name = "code_from")
    private SourceType codeFrom;

    @Column(name = "source")
    private Long userSource;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "Asignar_Norma_Politica",
//            joinColumns = {@JoinColumn(name = "policy_id")},
//            inverseJoinColumns = {@JoinColumn(name = "area_id")}
//    )
//    private Set<Area> areas;

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Lob
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SourceType getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(SourceType codeFrom) {
        this.codeFrom = codeFrom;
    }

    public Long getUserSource() {
        return userSource;
    }

    public void setUserSource(Long userSource) {
        this.userSource = userSource;
    }

//    public Set<Area> getAreas() {
//        return areas;
//    }
//
//    public void setAreas(Set<Area> areas) {
//        this.areas = areas;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
