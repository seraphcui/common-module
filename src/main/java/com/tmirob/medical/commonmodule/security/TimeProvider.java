/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-8-22 上午11:20.
 */

package com.tmirob.medical.commonmodule.security;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date();
    }
}
