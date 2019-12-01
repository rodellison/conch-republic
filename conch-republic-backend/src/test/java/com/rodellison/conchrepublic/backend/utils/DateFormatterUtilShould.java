package com.rodellison.conchrepublic.backend.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Date Formatter Util should")
public class DateFormatterUtilShould {

    @Test
    @DisplayName("convert a set of MMM DD, YYYY from/to dates to YYYYMMDD equivalents")
    void convertDateTestStartAndEnd() {

        String testDate = "Jun 6, 2020 - Jun 30, 2020";

        String[] strStartAndEndDates = DateFormatterUtil.formatEventDates(testDate);

        assertEquals("20200606", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }

    @Test
    @DisplayName("convert a single MMM DD, YYYY date to YYYYMMDD equivalent")
    void convertDateTestSingleDate() {

        String testDate = "Jun 30, 2020";

        String[] strStartAndEndDates = DateFormatterUtil.formatEventDates(testDate);

        assertEquals("20200630", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }
}
