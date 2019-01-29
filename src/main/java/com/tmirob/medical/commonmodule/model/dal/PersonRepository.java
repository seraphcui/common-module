package com.tmirob.medical.commonmodule.model.dal;

import com.tmirob.medical.commonmodule.model.entity.Person;
import com.tmirob.medical.commonmodule.model.entity.Position;
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
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person>{
    Person findOneByUsername(String username);

    Person findOneById(Long personId);

    Person findOneByCode2d(String code2d);

    List<Person> findByRole(Person.RoleType roleType);

    @Query(value = "select person from Person person inner join person.fingerprints fp where fp.id = :fingerPrintId")
    Person findOneByFingerPrintId(@Param("fingerPrintId") Long fingerPrintId);

    @Query(value = "select person from Person person inner join person.position position where position.name = :name")
    List<Person> findByPositionName(@Param("name") String room);

    @Query(value = "select person from Person person inner join person.position position where position.type = :type")
    List<Person> findByPositionType(@Param("type") Position.Type type);
}
