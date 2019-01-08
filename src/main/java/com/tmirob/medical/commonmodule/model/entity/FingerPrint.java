package com.tmirob.medical.commonmodule.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/11/6
 */
@Entity
public class FingerPrint implements Serializable {

    public enum FingerPrintStatus {
        /*未采集*/
        New("未采集"),
        /*采集中*/
        InProcess("采集中"),
        /*采集成功*/
        Success("采集成功"),
        /*采集失败*/
        Fail("采集失败");

        private String value;

        FingerPrintStatus(String status) {
            this.value = status;
        }
    }

    @javax.persistence.Id
    @GeneratedValue(generator = "IdGenerator")
    @GenericGenerator(name = "IdGenerator", strategy = "com.tmirob.medical.commonmodule.model.utility.CustomerIdGenerator")
    private Long id;
    private String name;
    private String feature;
    private FingerPrintStatus status;

    @ManyToOne
    @JoinColumn(name="personId", insertable = false, updatable = false)
    private Person person;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public FingerPrintStatus getStatus() {
        return status;
    }

    public void setStatus(FingerPrintStatus status) {
        this.status = status;
    }

}
