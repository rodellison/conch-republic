package net.rodellison.conchrepublic.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DateUtils {

    private static final Logger log = LogManager.getLogger(DateUtils.class);

    public static String convertMonth(String inMonth)
    {
        String returnMonth = "";
        switch (inMonth.toLowerCase())
        {
            case "jan":
            case "enero":
            case "january":
                returnMonth = "01";
                break;
            case "feb":
            case "febrero":
            case "february":
                returnMonth = "02";
                break;
            case "mar":
            case "marzo":
            case "march":
                returnMonth = "03";
                break;
            case "apr":
            case "abril":
            case "april":
                returnMonth = "04";
                break;
            case "may":
            case "mayo":
                returnMonth = "05";
                break;
            case "jun":
            case "junio":
            case "june":
                returnMonth = "06";
                break;
            case "jul":
            case "julio":
            case "july":
                returnMonth = "07";
                break;
            case "aug":
            case "agosto":
            case "august":
                returnMonth = "08";
                break;
            case "sep":
            case "septiembre":
            case "sept":
            case "september":
                returnMonth = "09";
                break;
            case "oct":
            case "octubre":
            case "october":
                returnMonth = "10";
                break;
            case "nov":
            case "noviembre":
            case "november":
                returnMonth = "11";
                break;
            case "dec":
            case "diciembre":
            case "december":
                returnMonth = "12";
                break;
            default:
                returnMonth = getFormattedTodayDate().substring(4,6);
        }

        return returnMonth;

    }

    public static String getFormattedTodayDate() {

        //This routine is for getting todays date in yyyyMMdd format, for use in scanning/queries in DynamoDB

        Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
        Calendar cal = Calendar.getInstance();  //calendar is 0 based for months so Jan = month 0, February = month 1, etc.
        cal.setTime(today);
        int month, year, day;
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        String formattedDate = String.format("%d%02d%02d", year, month, day);

        return formattedDate;
    }

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
                log.error(DateUtils.class.getName() + "Error attempting date formatting Request 1: " + inputDateValues[0]);
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
                log.error(DateUtils.class.getName() + "Error attempting date formatting Request 1: " + inputDateValues[0]);
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
        inDate = inDate.replace("Jun ", "June ");
        inDate = inDate.replace("Jul ", "July ");
        inDate = inDate.replace("Aug ", "August ");
        inDate = inDate.replace("Sep ", "September ");
        inDate = inDate.replace("Oct ", "October ");
        inDate = inDate.replace("Nov ", "November ");
        inDate = inDate.replace("Dec ", "December ");
        return inDate;
    }

    public static String convertNumericDateToFormatted(String numericDate, String languageLocale)
    {
        LocalDate localDate = LocalDate.of(Integer.valueOf(numericDate.substring(0,4)),
                Integer.valueOf(numericDate.substring(4,6)),
                Integer.valueOf(numericDate.substring(6)));

        if (languageLocale.contains("es"))  //if Spanish, reverse month and year
            return String.format("%02d", localDate.getDayOfMonth()) + "/" + String.format("%02d", localDate.getMonthValue()) + "/" + localDate.getYear();
        else
            return String.format("%02d", localDate.getMonthValue()) + "/" + String.format("%02d", localDate.getDayOfMonth()) + "/" + localDate.getYear();

    }

}
