package com.tmirob.medical.commonmodule.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmirob.medical.commonmodule.model.utility.ICustomerId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/19
 */

@Entity
public class Person implements ICustomerId<Long> {

    public enum RoleType {
        /*
        * 药房管理员
        */
        PharmacyAdmin,
        /**
         * 麻醉医生,
         */
        Anesthesiologist,
        /*
        * 管理员
        */
        Admin,
        /*
         * 超级管理员
         */
        Root

    }

    @javax.persistence.Id
    @GeneratedValue(generator = "IdGenerator")
    @GenericGenerator(name = "IdGenerator", strategy = "com.tmirob.medical.commonmodule.model.utility.CustomerIdGenerator")
    private Long id;
    // 真实姓名
    @Column(nullable = false)
    private String name;
    // 登陆用户名
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    /* 工号 */
    private String jobNumber;
    /* 二维码 */
    private String code2d;
    /* 管辖位置 */
    @ManyToOne
    @JoinColumn(name = "positionId")
    private Position position;

    private String cellphone;

    @Column(nullable = false)
    private RoleType role;

    private boolean online = true;

    /**
     * 电子签名图片url
     */
    private String signature;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "person", targetEntity = FingerPrint.class)
    private List<FingerPrint> fingerprints;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getCode2d() {
        return code2d;
    }

    public void setCode2d(String code2d) {
        this.code2d = code2d;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<FingerPrint> getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(List<FingerPrint> fingerprints) {
        this.fingerprints = fingerprints;
    }

    public void addFingerprint(FingerPrint fingerPrint) {
        this.fingerprints.add(fingerPrint);
    }

    public void deleteFingerprint(Long fingerPrintId) {
        for (FingerPrint fingerPrint : this.fingerprints) {
            if (fingerPrintId == fingerPrint.getId()) {
                this.fingerprints.remove(fingerPrint);
                break;
            }
        }
    }

}
