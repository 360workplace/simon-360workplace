package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "Norma_Politica")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code_from")
    private String codeFrom;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source", referencedColumnName = "id")
    private User userSource;

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

    @Size(max = 1024, message = "The content is too long.")
    @Column(name = "detail", columnDefinition = "text")
    @NotBlank(message = "The detail is mandatory")
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeFrom() {
        return codeFrom;
    }

    public void setCodeFrom(String codeFrom) {
        this.codeFrom = codeFrom;
    }

    public User getUserSource() {
        return userSource;
    }

    public void setUserSource(User userSource) {
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
