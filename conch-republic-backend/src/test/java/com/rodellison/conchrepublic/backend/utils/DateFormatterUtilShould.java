package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Date Formatter Util should")
public class DateFormatterUtilShould {

    @Test
    @DisplayName("convert a MMM DD, YYYY oriented set of dates to YYYYMMDD")
    void convertDateTestStartAndEnd() {

        String testDate = "Jun 6, 2020 - Jun 30, 2020";

        String[] strStartAndEndDates = DateFormatterUtil.formatEventDates(testDate);

        assertEquals("20200606", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }

    @Test
    @DisplayName("convert a MMM DD, YYYY oriented single date to YYYYMMDD")
    void convertDateTestSingleDate() {

        String testDate = "Jun 30, 2020";

        String[] strStartAndEndDates = DateFormatterUtil.formatEventDates(testDate);

        assertEquals("20200630", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }
}
