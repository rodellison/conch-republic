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
        inDate = inDate.replace("Jan ", "January ");
        inDate = inDate.replace("Feb ", "February ");
        inDate = inDate.replace("Mar ", "March ");
        inDate = inDate.replace("Apr ", "April ");
  //      inDate = inDate.replace("May ", "May ");
        inDate = inDate.replace("Jun ", "June ");
        inDate = inDate.replace("Jul ", "July ");
        inDate = inDate.replace("Aug ", "August ");
        inDate = inDate.replace("Sep ", "September ");
        inDate = inDate.replace("Oct ", "October ");
        inDate = inDate.replace("Nov ", "November ");
        inDate = inDate.replace("Dec ", "December ");
        return inDate;
    }
}
