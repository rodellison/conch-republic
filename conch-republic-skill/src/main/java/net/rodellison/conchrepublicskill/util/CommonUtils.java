package net.rodellison.conchrepublicskill.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.SupportedInterfaces;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CommonUtils {

    public static String prepForSimpleStandardCardText(String text)
    {
        String returnText = text.replace("<br/>", "\n\n");
        returnText = returnText.replace("<p>", "");
        returnText = returnText.replace("</p>", "\n\n");
        returnText = returnText.replace("<s>", "");
        returnText = returnText.replace("</s>", "\n\n");
        returnText = returnText.replace("<b>", "");
        returnText = returnText.replace("</b>", "");
        returnText = returnText.replace("<font size='1'>", "");
        returnText = returnText.replace("</font>", "");
        returnText = returnText.replace("&", " and ");

        return returnText;
    }

    public static boolean supportsApl(HandlerInput input) {
        SupportedInterfaces supportedInterfaces = input.getRequestEnvelope().getContext().getSystem().getDevice().getSupportedInterfaces();
        return supportedInterfaces.getAlexaPresentationAPL() != null;
    }

    public static String toTitleCase(String givenString) {

        if (givenString.trim() == "")
            return "";

        String tempString = givenString;

        String[] arr = tempString.split(" ", 0);
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }

        return sb.toString().trim();
    }



}
