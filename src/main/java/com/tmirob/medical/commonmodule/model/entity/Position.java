package com.tmirob.medical.commonmodule.model.entity;

import com.tmirob.medical.commonmodule.model.utility.ICustomerId;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/19
 */
@Entity
public class Position implements ICustomerId<Long> {


    @javax.persistence.Id
    @GeneratedValue(generator = "IdGenerator")
    @GenericGenerator(name = "IdGenerator", strategy = "com.tmirob.medical.commonmodule.model.utility.CustomerIdGenerator")
    private Long id;
    @Column(nullable = false)

    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    private String floor;

    @Override
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public enum Type {
        /*
        * 手术室
        */
        OperatingRoom("手术室"),
        /*
        * 管控冷链类机器人
        */
        ControlledPharmacy("管控冷链药房");

        private String positionType;

        Type(String type) {
            this.positionType = type;
        }
    }
}

