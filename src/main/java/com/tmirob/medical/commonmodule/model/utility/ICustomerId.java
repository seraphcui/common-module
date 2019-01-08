package com.tmirob.medical.commonmodule.model.utility;

import java.io.Serializable;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/18
 */
public interface ICustomerId<T extends Serializable> {
    T getId();
}
