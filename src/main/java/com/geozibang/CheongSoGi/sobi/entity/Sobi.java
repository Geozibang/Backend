package com.geozibang.CheongSoGi.sobi.entity;

import com.geozibang.CheongSoGi.date.entity.Date;
import com.geozibang.CheongSoGi.sobi.dto.SobiDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="SOBI")
public class Sobi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SOBI_ID")
    private Long id;

    @Column(name="SOBI_RANK")
    private Long ranking;

    @Column(name="SOBI_CATEGORY")
    private String category;

    @Column(name="SOBI_NAME")
    private String sobiname;

    @Column(name="SOBI_MONEY")
    private Long money;

    @Column(name="SOBI_CHECK")
    private Long checking;

    @ManyToOne
    @JoinColumn(name="DATE_ID", referencedColumnName = "DATE_ID")
    private Date date;

    @Builder
    public Sobi(Long id, Long ranking, String category, String sobiname, Long money, Long checking ,Date date){
        this.id = id;
        this.ranking = ranking;
        this.category = category;
        this.sobiname = sobiname;
        this.money = money;
        this.checking = checking;
        this.date = date;
    }

    public SobiDto toDto(){
        return SobiDto.builder()
                .id(this.id)
                .ranking(this.ranking)
                .category(this.category)
                .sobiname(this.sobiname)
                .money(this.money)
                .checking(this.checking)
                .date(this.date)
                .build();
    }

}

