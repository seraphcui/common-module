package com.tmirob.medical.commonmodule.model.utility;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IncrementGenerator;

import java.io.Serializable;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/18
 */
public class CustomerIdGenerator extends IncrementGenerator {
    /*
    Must say here is a very tricky problem:
    NOTICE: If this is the very first time to use super.generate() for a certain entity since server start,
    the session would be created, and when creating the session it would get the most recent id from database,
    but remember, this is the only chance session would sync with database.
    So the problem is that we use super.generate() first and then use a given id,
    the session won't update and still stick the value before we use a given id, which would cause a problem.
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if (object == null) {
            throw new HibernateException(new NullPointerException()) ;
        }

        ICustomerId<Long> entity = (ICustomerId<Long>) object;
        if(entity == null || entity.getId() == null){
            Serializable value = super.generate(session, entity);
            return value;
        }else{
            return entity.getId();
        }
    }
}
