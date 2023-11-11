package com.geozibang.CheongSoGi.sobi.service;

import com.geozibang.CheongSoGi.date.dto.DateDto;
import com.geozibang.CheongSoGi.date.entity.Date;
import com.geozibang.CheongSoGi.date.repository.DateRepository;
import com.geozibang.CheongSoGi.sobi.dto.SobiDto;
import com.geozibang.CheongSoGi.sobi.entity.Sobi;
import com.geozibang.CheongSoGi.sobi.repository.SobiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SobiService {
    private final SobiRepository sobiRepository;
    private final DateRepository dateRepository;

    public Sobi createSobi(SobiDto sobiDto){

        DateDto dateDto = sobiDto.getDate().toDto();
        Date date;

        if(dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), dateDto.getDay()) == null){
            date = dateRepository.save(Date.builder()
                    .year(sobiDto.getDate().getYear())
                    .month(sobiDto.getDate().getMonth())
                    .day(sobiDto.getDate().getDay())
                    .build());

            return sobiRepository.save(Sobi.builder()
                    .id(sobiDto.getId())
                    .ranking(sobiDto.getRanking())
                    .category(sobiDto.getCategory())
                    .sobiname(sobiDto.getSobiname())
                    .money(sobiDto.getMoney())
                    .checking(sobiDto.getChecking())
                    .date(date)
                    .build());
        }else{
            return sobiRepository.save(Sobi.builder()
                    .id(sobiDto.getId())
                    .ranking(sobiDto.getRanking())
                    .category(sobiDto.getCategory())
                    .sobiname(sobiDto.getSobiname())
                    .money(sobiDto.getMoney())
                    .checking(sobiDto.getChecking())
                    .date(dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), dateDto.getDay()))
                    .build());
        }
    }

    public List<Sobi> findAll(){
        return sobiRepository.findAll();
    }

    public List<Sobi> findAllbyDate(DateDto dateDto){

        Date targetDate = dateRepository.findByYearMonthDay(dateDto.getYear(), dateDto.getMonth(), dateDto.getDay());

        List<Sobi> targetSobiList = sobiRepository.findAllByDateId(targetDate.getId());

        return targetSobiList;
    }


    public String update(Long id, SobiDto sobiDto){

        Optional<Sobi> sobi = sobiRepository.findById(id);
        String msg = sobi.get().getSobiname() + "가 ";

        sobiDto.setId(sobi.get().getId());
        if(!sobiDto.getSobiname().equals(sobi.get().getSobiname())){
            msg = msg + sobiDto.getSobiname() + "로 ";
        }

        createSobi(sobiDto);

        msg = msg + "변경되었습니다.";

        return msg;
    }

    public Sobi updateCheck(Long id){
        Optional<Sobi> sobi = sobiRepository.findById(id);
        sobi.get().setChecking(1L);

        return createSobi(sobi.get().toDto());
    }

    public String delete(Long id){

        Optional<Sobi> sobi = sobiRepository.findById(id);
        String msg = sobi.get().getSobiname() + "가 삭제되었습니다.";
        System.out.println(msg);

        sobiRepository.deleteById(id);

        // if last tuple at that day, delete date tuple.
        Long targetDateId = dateRepository.findById(sobi.get().getDate().getId()).get().getId();
        if(sobiRepository.findAllByDateId(targetDateId).isEmpty()){
            dateRepository.deleteById(targetDateId);
        }

        return msg;
    }
}
