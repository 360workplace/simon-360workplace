package com.workplace.simon.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "depend")
    private Long depend;

    @OneToOne(mappedBy = "area")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepend() {
        return depend;
    }

    public void setDepend(Long depend) {
        this.depend = depend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
