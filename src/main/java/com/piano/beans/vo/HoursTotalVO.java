package com.piano.beans.vo;

import com.piano.beans.db.DailyCheck;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class HoursTotalVO {
    private BigDecimal total;
    private BigDecimal today;
    private List<DailyCheck> week;
    private List<DailyCheck> month;
    private List<DailyCheck> year;

}
