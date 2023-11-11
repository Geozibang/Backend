package com.geozibang.CheongSoGi.date.repository;

import com.geozibang.CheongSoGi.date.entity.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DateRepository extends JpaRepository<Date, Long> {

    List<Date> findAllByYear(Long year);
    List<Date> findAllByMonth(Long month);
    Date findByDay(Long date);

    @Query(
            value = "select * from sobi_date where year = :year and month = :month and day = :day"
            , nativeQuery = true
    )
    Date findByYearMonthDay(@Param("year")Long year, @Param("month")Long month, @Param("day")Long day);

    @Query(
            value = "select * from sobi_date where year = :year and month = :month"
            , nativeQuery = true
    )
    List<Date> findAllByYearMonth(@Param("year") Long year, @Param("month")Long month);

//    @Query(
//            value = "select * from Date where day = :day"
//            , nativeQuery = true
//    )
//    List<Date> findAllByDay(@Param("day")Long day);
}
