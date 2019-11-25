package com.rodellison.conchrepublic.backend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DateFormatterUtil {

    private static final Logger log = LogManager.getLogger(DateFormatterUtil.class);

    public static String[] formatEventDates(String inputDateString)
    {
        String[] inputDateValues = inputDateString.split(" - ");
        String[] returnDates = {"", ""};
        String startDate = "";
        String endDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        if (inputDateValues.length == 2)   //we have a different start and end date..
        {
            try
            {
                Date startDateDT1 =sdf.parse(convertShortDate(inputDateValues[0]));
                Date endDateDT1 =sdf.parse(convertShortDate(inputDateValues[1]));
                startDate = sdf2.format(startDateDT1);
                endDate = sdf2.format(endDateDT1);

            } catch (ParseException pe)
            {
                log.error(DateFormatterUtil.class.getName() + "Error attempting date formatting Request 1: " + inputDateValues[0]);
            }
        }
        else
        {
            try
            {
                Date startDateDT1 =sdf.parse(convertShortDate(inputDateValues[0]));
                startDate = sdf2.format(startDateDT1);
                endDate = startDate;

            } catch (ParseException pe)
            {
                log.error(DateFormatterUtil.class.getName() + "Error attempting date formatting Request 1: " + inputDateValues[0]);
            }
        }
        returnDates[0] = startDate;
        returnDates[1] = endDate;

        return returnDates;

    }

    private static String convertShortDate(String inDate)
    {
        String tempDate = inDate;
        tempDate.replace("Jan ", "January ");
        tempDate.replace("Feb ", "February ");
        tempDate.replace("Mar ", "March ");
        tempDate.replace("Apr ", "April ");
        tempDate.replace("May ", "May ");
        tempDate.replace("Jun ", "June ");
        tempDate.replace("Jul ", "July ");
        tempDate.replace("Aug ", "August ");
        tempDate.replace("Sep ", "September ");
        tempDate.replace("Oct ", "October ");
        tempDate.replace("Nov ", "November ");
        tempDate.replace("Dec ", "December ");
        return tempDate;
    }
}
