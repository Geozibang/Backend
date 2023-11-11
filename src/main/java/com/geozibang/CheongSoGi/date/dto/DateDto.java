package com.geozibang.CheongSoGi.date.dto;

import com.geozibang.CheongSoGi.sobi.entity.Sobi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateDto {
    private Long id;
    private Long year;
    private Long month;
    private Long day;
    private List<Sobi> sobiList;
}
