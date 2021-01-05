package com.workplace.simon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> roles;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @Column(name = "supervisor_id")
    private Long supervisor;

//    @OneToOne(mappedBy = "supervisor")
//    private User backSupervisor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area;

//    @OneToOne(mappedBy = "supervisor")
//    private Execution execution;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //    public Long getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Long roleId) {
//        this.roleId = roleId;
//    }

    public Long getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Long supervisor) {
        this.supervisor = supervisor;
    }

//    public User getBackSupervisor() {
//        return backSupervisor;
//    }
//
//    public void setBackSupervisor(User backSupervisor) {
//        this.backSupervisor = backSupervisor;
//    }

//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area area) {
//        this.area = area;
//    }

//    public Execution getExecution() {
//        return execution;
//    }
//
//    public void setExecution(Execution execution) {
//        this.execution = execution;
//    }
}
