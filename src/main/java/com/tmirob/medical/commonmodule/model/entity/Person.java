package com.tmirob.medical.commonmodule.model.entity;

import com.tmirob.medical.commonmodule.model.utility.ICustomerId;
import org.hibernate.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

    /**
     * 电子签名图片url
     */
    private String signature;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true,
            fetch = FetchType.EAGER, mappedBy = "person", targetEntity = FingerPrint.class)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FingerPrint> fingerprints;

    /**
     * 是否可以一键呼叫机器人。
     * 默认是false。主任医师、副主任医师、主治医师设置为true。
     */
    private boolean oneButtonCallRobot = false;

    /**
     * 账号是否被锁。
     * 默认是false。当申领阈值超过设置或者操作完成没有关闭抽屉，系统会自动锁账号。
     */
    private boolean isLocked = false;

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
            if (fingerPrintId.equals(fingerPrint.getId())) {
                this.fingerprints.remove(fingerPrint);
                break;
            }
        }
    }

    public boolean isOneButtonCallRobot() {
        return oneButtonCallRobot;
    }

    public void setOneButtonCallRobot(boolean oneButtonCallRobot) {
        this.oneButtonCallRobot = oneButtonCallRobot;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
