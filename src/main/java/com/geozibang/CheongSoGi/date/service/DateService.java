package com.geozibang.CheongSoGi.date.service;

import com.geozibang.CheongSoGi.date.dto.DateDto;
import com.geozibang.CheongSoGi.date.entity.Date;
import com.geozibang.CheongSoGi.date.repository.DateRepository;
import com.geozibang.CheongSoGi.sobi.entity.Sobi;
import com.geozibang.CheongSoGi.sobi.repository.SobiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DateService {

    private final DateRepository dateRepository;
    private final SobiRepository sobiRepository;

    public Date createDate(DateDto dateDto){
        return dateRepository.save(Date.builder()
                .year(dateDto.getYear())
                .month(dateDto.getMonth())
                .day(dateDto.getDay()).build());
    }

    public Date readByYearMonthDay(DateDto dateDto){
        return dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), dateDto.getDay());
    }

    public List<Date> readAllByYear(DateDto dateDto){
        return dateRepository.findAllByYear(dateDto.getYear());
    }


    public Long readMoneyByWeek(DateDto dateDto){

        List<Sobi> SobiByDay;
        Long DaySum = 0L;

        // Sum of Last Day
        if(dateDto.getDay() - 7 > 0){
            long nowDate = dateDto.getDay();
            for(long i = nowDate ; i > nowDate-7 ; i--){
                Date targetDay = dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), i);
                if(targetDay == null) continue;

                SobiByDay = sobiRepository.findAllByDateId(targetDay.getId());
                for(Sobi targetSobi : SobiByDay){
                    if(targetSobi.getChecking() == 1)
                        DaySum += targetSobi.getMoney();
                }
            }
        }else {
            long nowDate = dateDto.getDay();
            for(long i = nowDate ; i > nowDate - 7 ; i--){
                if(i > 0 ){
                    Date targetDay = dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), i);
                    if(targetDay == null) continue;
                    SobiByDay = sobiRepository.findAllByDateId(targetDay.getId());
                    for(Sobi targetSobi : SobiByDay){
                        if(targetSobi.getChecking() == 1)
                            DaySum += targetSobi.getMoney();
                    }
                }else{
                    if(dateDto.getMonth() == 1L){
                        Date targetDay = dateRepository.findByYearMonthDay(dateDto.getYear()-1, 12L, i+31);
                        if(targetDay == null) continue;
                        SobiByDay = sobiRepository.findAllByDateId(targetDay.getId());
                        for(Sobi targetSobi : SobiByDay){
                            if(targetSobi.getChecking() == 1)
                                DaySum += targetSobi.getMoney();
                        }
                    }else{
                        Date targetDay = dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth()-1, i+31);
                        if(targetDay == null) continue;
                        SobiByDay = sobiRepository.findAllByDateId(targetDay.getId());
                        for(Sobi targetSobi : SobiByDay){
                            if(targetSobi.getChecking() == 1)
                                DaySum += targetSobi.getMoney();
                        }
                    }
                }
            }
        }

        return DaySum;
    }
    public Long readMoneyByMonth(DateDto dateDto){

        List<Date> LastMonth;
        List<Sobi> SobiByDateList;
        Long MonthSum = 0L;

        if(dateDto.getMonth()==1L){
            LastMonth = dateRepository.findAllByYearMonth(dateDto.getYear()-1, 12L);
        }else{
            LastMonth = dateRepository.findAllByYearMonth(dateDto.getYear(), dateDto.getMonth()-1);
        }

        // Sum of Last Month
        for(Date targetDate : LastMonth){
            SobiByDateList = (sobiRepository.findAllByDateId(targetDate.getId()));
            for(Sobi targetSobi : SobiByDateList){
                if(targetSobi.getChecking() == 1L)
                    MonthSum += targetSobi.getMoney();
            }
        }

        return MonthSum;
    }


    public HashMap<Long, Long> readCalender(Long year, Long month){

        Long DaySum;
        HashMap<Long, Long> calender = new HashMap<>();

        List<Date> targetDateList = dateRepository.findAllByYearMonth(year, month);

        for(Date targetDate : targetDateList){
            List<Sobi> targetSobiList = sobiRepository.findAllByDateId(targetDate.getId());
            DaySum = 0L;
            for(Sobi targetSobi : targetSobiList){
                if(targetSobi.getChecking() == 1)
                    DaySum += targetSobi.getMoney();
            }
            calender.put(targetDate.getDay(), DaySum);
        }

        return calender;
    }
}
