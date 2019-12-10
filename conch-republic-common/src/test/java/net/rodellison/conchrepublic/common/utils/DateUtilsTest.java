package net.rodellison.conchrepublic.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Date Utils should")
class DateUtilsTest {

    @Test
    void convertMonth() {

        String monthToTest = "December";
        assertEquals("12", DateUtils.convertMonth(monthToTest));
    }


    @Test
    @DisplayName("convert a set of MMM DD, YYYY from/to dates to YYYYMMDD equivalents")
    void convertDateTestStartAndEnd() {

        String testDate = "Jun 6, 2020 - Jun 30, 2020";

        String[] strStartAndEndDates = DateUtils.formatEventDates(testDate);

        assertEquals("20200606", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }

    @Test
    @DisplayName("convert a single MMM DD, YYYY date to YYYYMMDD equivalent")
    void convertDateTestSingleDate() {

        String testDate = "Jun 30, 2020";

        String[] strStartAndEndDates = DateUtils.formatEventDates(testDate);

        assertEquals("20200630", strStartAndEndDates[0]);
        assertEquals("20200630", strStartAndEndDates[1]);

    }

    @Test
    @DisplayName("convert a numeric date in yyyyMMdd to MM/DD/YYYY")
    void convertNumericDateToFormatted() {

        String testDate = "20201231";
        String convertedDate = DateUtils.convertNumericDateToFormatted(testDate);
        assertEquals("12/31/2020", convertedDate);

        testDate = "20200131";
        convertedDate = DateUtils.convertNumericDateToFormatted(testDate);
        assertEquals("01/31/2020", convertedDate);

        testDate = "20200229";
        convertedDate = DateUtils.convertNumericDateToFormatted(testDate);
        assertEquals("02/29/2020", convertedDate);

        testDate = "20200329";
        convertedDate = DateUtils.convertNumericDateToFormatted(testDate);
        assertEquals("03/29/2020", convertedDate);

        testDate = "20201031";
        convertedDate = DateUtils.convertNumericDateToFormatted(testDate);
        assertEquals("10/31/2020", convertedDate);

    }


}