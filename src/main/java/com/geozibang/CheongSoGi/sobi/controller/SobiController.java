package com.geozibang.CheongSoGi.sobi.controller;

import com.geozibang.CheongSoGi.date.dto.DateDto;
import com.geozibang.CheongSoGi.sobi.dto.SobiDto;
import com.geozibang.CheongSoGi.sobi.entity.Sobi;
import com.geozibang.CheongSoGi.sobi.service.SobiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sobi")
public class SobiController {
    private final SobiService sobiService;

    /*--------------- 소비생성 화면 -------------*/
    // 소비 생성 버튼 클릭할 때,
    @PostMapping("/create")
    public ResponseEntity<Sobi> saveSobi(
            @RequestBody SobiDto sobiDto
    ){
        return ResponseEntity.ok(sobiService.createSobi(sobiDto));
    }


    /* -------------- 소비보기 화면 -------------*/

    // 소비보기 화면에서 전체 소비 볼 때,
    @GetMapping("/readbydate")
    public ResponseEntity<List<Sobi>> readSobi(
            @RequestBody DateDto dateDto
    ){
        return ResponseEntity.ok(sobiService.findAllbyDate(dateDto));
    }

    // 사용 x
    @GetMapping("/readAll")
    public ResponseEntity<List<Sobi>> readAllSobi(
    ){
        return ResponseEntity.ok(sobiService.findAll());
    }

    // 소비 수정버튼 클릭할 때,
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateSobi(
            @PathVariable(name = "id") Long id,
            @RequestBody SobiDto sobiDto
    ){
        return ResponseEntity.ok(sobiService.update(id, sobiDto));
    }

    // check 버튼 클릭할 때,
    @PostMapping("/updateChecking/{id}")
    public ResponseEntity<?> updateChecking(
            @PathVariable(name="id") Long id
    ){
        return ResponseEntity.ok(sobiService.updateCheck(id));
    }

    // 소비 삭제할 때,
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSobi(
            @PathVariable("id") Long id
    ){
        System.out.println(id + "\n");
        return ResponseEntity.ok(sobiService.delete(id));
    }

    /* -------------- 달력보기화면 ---------------*/

    // 달력보기 화면에서 날짜 클릭할 때, -> 해당 날짜의 소비들 반환
    @GetMapping("/month/readDate")
    public ResponseEntity<List<Sobi>> MonthreadDate(
            @RequestBody DateDto dateDto
    ){
        List<Sobi> targetMonth = sobiService.findAllbyDate(dateDto);
        List<Sobi> targetDates = new ArrayList<>();
        for(Sobi targetDate : targetMonth){
            if(targetDate.getChecking() == 1){
                targetDates.add(targetDate);
            }
        }

        return ResponseEntity.ok(targetDates);
    }

}

