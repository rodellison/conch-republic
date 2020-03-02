package net.rodellison.conchrepublicskill.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.SupportedInterfaces;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.rodellison.conchrepublicskill.handlers.CustomLaunchRequestHandler;
import net.rodellison.conchrepublicskill.models.LanguageLocalization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class CommonUtils {

    private static final Logger log = LogManager.getLogger(CommonUtils.class);
    public static String savedLocale;

    public static String prepForSSMLSpeech(String text) {
        String returnText = text.replace("& ", "and ");
        returnText = returnText.replace("&nbsp;", " ");
        returnText = returnText.replace("<a href=\"", " ");
        returnText = returnText.replace("\">", " ");
        returnText = returnText.replace("here</a>", " ");
        returnText = returnText.replace("</a>", " ");

        return returnText;
    }

    public static String prepForSSMLSpeechForSpanish(String text) {
        String returnText = text.replace("& ", "y ");
        returnText = text.replace("and ", "y ");
        returnText = returnText.replace("«", "");  //AWS Amazon Translate may come back with these for quotes in spanish
        returnText = returnText.replace("»", "");

        return returnText;
    }


    public static String prepForSimpleStandardCardText(String text) {
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

    public static LanguageLocalization getLocalizationStrings(String locale) {
        Gson gson = new Gson();
        LanguageLocalization theLanguageLocalization;
        try {
            JsonReader reader;
            if (locale.equals("es-US")) //US Spanish
                reader = new JsonReader(new FileReader("localization-es-US.json"));
            else
                reader = new JsonReader(new FileReader("localization-en-US.json"));

            theLanguageLocalization = gson.fromJson(reader, LanguageLocalization.class);
            reader.close();
            savedLocale = locale;
            return theLanguageLocalization;

        } catch (IOException e) {
            log.error("Failed in loading localization.json file");
            e.printStackTrace();
        }
        return null;

    }
}
