package com.tmirob.medical.commonmodule.model.dal;

import com.tmirob.medical.commonmodule.model.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/11/1
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {

    Position findOneById(Long positionId);

    List<Position> findByType(Position.Type type);

    List<Position> findByTypeIn(List<Position.Type> types);
}
