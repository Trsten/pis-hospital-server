package com.hospital.restapi.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "systemuser_seq_gen")
    @SequenceGenerator(name = "systemuser_seq_gen", sequenceName = "systemuser_iduser_seq", allocationSize = 1)
    private Long idUser;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Boolean isAdmin;

    @Transient
    private Long idGroup;

    @Transient
    private Long idStaff;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "idGroup")
    private UserGroup userGroup;

    @OneToOne
    @JsonbTransient
    @JoinColumn(name = "idStaff")
    private MedicalStaff medicalStaff;

    @MayReturnNull
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @MayReturnNull
    public Long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
    }

    @MayReturnNull
    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
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

    @MayReturnNull
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @MayReturnNull
    public MedicalStaff getMedicalStaff() {
        return medicalStaff;
    }

    public void setMedicalStaff(MedicalStaff medicalStaff) {
        this.medicalStaff = medicalStaff;
    }

}
