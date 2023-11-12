package com.geozibang.CheongSoGi.date.controller;

import com.geozibang.CheongSoGi.date.dto.DateDto;
import com.geozibang.CheongSoGi.date.entity.Date;
import com.geozibang.CheongSoGi.date.entity.LastSum;
import com.geozibang.CheongSoGi.date.service.DateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/date")
@CrossOrigin(origins = "*")
public class DateController {

    private final DateService dateService;

    // 사용 x
    @GetMapping("/read/year")
    public ResponseEntity<List<Date>> readbyyear(
            @RequestBody DateDto dateDto
    ){
        return ResponseEntity.ok(dateService.readAllByYear(dateDto));
    }

    // 사용 x
    @GetMapping("/read/yearmonthday")
    public ResponseEntity<Date> readbyyearmonthday(
            @RequestBody DateDto dateDto
    ){
        return ResponseEntity.ok(dateService.readByYearMonthDay(dateDto));
    }

    /*----------------- 지출보기 화면 ----------------*/
    // 지출 보기 화면 클릭할 때, 보이는 기본화면 -> 지난 달 합계 / 지난 주 합계
    @GetMapping("/readMoney/last")
    public ResponseEntity<?> readMoneyByDate(
            @RequestBody DateDto dateDto
    ){
        LastSum lastSum = new LastSum();

        lastSum.setLastMonthSum(dateService.readMoneyByMonth(dateDto));
        lastSum.setLastWeekSum(dateService.readMoneyByWeek(dateDto));

        return ResponseEntity.ok(lastSum);
    }

    /*----------------- 달력보기 화면 ---------------*/
    // 달력에 날짜별로 합계 볼 때, -> 날짜와 합계 hashmap으로 반환
    @GetMapping("/calender/{year}/{month}")
    public ResponseEntity<?> readCalenderByMonth(
            @PathVariable(name = "year") Long year,
            @PathVariable(name = "month") Long month
    ){
        return ResponseEntity.ok(dateService.readCalender(year, month));
    }
}


