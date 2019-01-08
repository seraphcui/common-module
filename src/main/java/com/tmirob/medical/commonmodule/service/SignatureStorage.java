/*
 * Copyright (c) 2018. All rights reserved by Taimi Robot.
 * Created by yaocui on 18-10-23 下午5:56.
 */

package com.tmirob.medical.commonmodule.service;

import org.springframework.stereotype.Service;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/23
 */
@Service
public class SignatureStorage extends StorageService {
    public SignatureStorage() {
        this.location = this.rootLocation.resolve("signature");
    }
}
