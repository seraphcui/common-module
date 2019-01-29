package com.tmirob.medical.commonmodule.model.dal;

import com.tmirob.medical.commonmodule.model.entity.Person;
import com.tmirob.medical.commonmodule.model.entity.FingerPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/19
 */
@Repository
public interface FingerPrintRepository extends JpaRepository<FingerPrint, Long>, JpaSpecificationExecutor<FingerPrint>{
    FingerPrint findFingerById(Long id);

    @Query(value = "select f from FingerPrint f where f.id = (select max(f2.id) from FingerPrint f2)")
    FingerPrint findMaxIdFingerPrint();
}
