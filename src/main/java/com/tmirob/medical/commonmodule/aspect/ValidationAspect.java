/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-9-11 下午1:30.
 */

package com.tmirob.medical.commonmodule.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

import static com.tmirob.medical.commonmodule.model.utility.ErrorCode.ValidationFail;
import static com.tmirob.medical.commonmodule.model.utility.IConstants.RETURN_RESULT;
import static com.tmirob.medical.commonmodule.model.utility.IConstants.RETURN_VALUE;

@Aspect
@Component
public abstract class ValidationAspect {

    public Object executeValidation(ProceedingJoinPoint proceedingJoinPoint, BindingResult bindingResult) throws Throwable{
        Map<String, Object> m = new HashMap<>();
        if (bindingResult.hasErrors()) {
            m.put(RETURN_VALUE, ValidationFail.getCode());
            m.put(RETURN_RESULT, bindingResult.getAllErrors());
        }
        else {
            try {
                m = (Map<String, Object>) proceedingJoinPoint.proceed();
            }
            catch (Throwable throwable){
                throw throwable;
            }
        }
        return m;
    }
}
