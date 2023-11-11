package com.geozibang.CheongSoGi.sobi.repository;

import com.geozibang.CheongSoGi.sobi.entity.Sobi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SobiRepository extends JpaRepository<Sobi, Long> {
    List<Sobi> findAllByDateId(Long DateId);
}

