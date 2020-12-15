package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    @NotBlank(message = "lastname is mandatory")
    private String lastname;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "role_id")
    private Long roleId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private User supervisor;

    @OneToOne(mappedBy = "supervisor")
    private User backSupervisor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

    @OneToOne(mappedBy = "supervisor")
    private Execution execution;

    @OneToOne(mappedBy = "userSource")
    private Policy policy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public User getBackSupervisor() {
        return backSupervisor;
    }

    public void setBackSupervisor(User backSupervisor) {
        this.backSupervisor = backSupervisor;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
