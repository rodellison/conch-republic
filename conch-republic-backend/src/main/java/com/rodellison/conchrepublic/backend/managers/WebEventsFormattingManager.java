package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventsList;
import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.model.KeysLocations;
import com.rodellison.conchrepublic.backend.utils.DateFormatterUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.*;


public class WebEventsFormattingManager {

    private final String LISTING_BLOCK = "listing-block listing-calendar";
    private final String LISTING_BLOCK_WITH_IMG = "listing-block listing-calendar listing-calendar-img";
    private final String LISTING_IMG = "swipebox expand-img";
    private final String LISTING_DESCRIPTION = "listing-desc";
    private final String LISTING_LOCATION = "Listing-location";  //unsure why this is capitalized, others are not...
    private final String LISTING_DATE = "Listing-date";  //unsure why this is capitalized, others are not...
    private final String LISTING_NAME = "listing-name";
    private final String LISTING_PHONE = "listing-phone";


    public List<EventItem> convertRawHTMLToEventList(ArrayList<String> rawEventDataList) {

        EventsList theEventList = new EventsList();
        Map<String, EventItem> theEventsMap = new HashMap<>() ;

        Elements elementsNoImg = null;
        Elements elementsWithImg = null;
        Elements allElements = new Elements();
        for (String rawHTMLString : rawEventDataList) {
            Document doc = Jsoup.parse(rawHTMLString);
            elementsNoImg = doc.getElementsByClass(LISTING_BLOCK);
            allElements.addAll(elementsNoImg);
            elementsWithImg = doc.getElementsByClass(LISTING_BLOCK_WITH_IMG);
            allElements.addAll(elementsWithImg);
        }

        for (Element thisElement : allElements) {
            EventItem thisEventItem = extractHTMLDataToElement(thisElement);
            if (!theEventsMap.containsKey(thisEventItem.getEventID()))
                theEventsMap.put(thisEventItem.getEventID(), thisEventItem);
        }

        theEventsMap.forEach((k,v) ->
        {
            theEventList.addEventItem(v);
        });

        return theEventList.getListOfEventsSortedByStartDate();

    }

    private EventItem extractHTMLDataToElement(Element thisEventElement) {
        EventItem thisEventItem = new EventItem();

        thisEventItem.setEventID(thisEventElement.attr("id"));

        Elements imgElements = thisEventElement.getElementsByClass(LISTING_IMG);
        if (imgElements.size() > 0)
        {
            thisEventItem.setEventImgURL(imgElements.attr("href"));
        }
        else
            thisEventItem.setEventImgURL("");

        Elements eventDescription = thisEventElement.getElementsByClass(LISTING_DESCRIPTION);
        if (eventDescription.size() > 0)
            thisEventItem.setEventDescription(eventDescription.html());

        Elements eventLocation = thisEventElement.getElementsByClass(LISTING_LOCATION);

        if (eventLocation.size() > 0)
            thisEventItem.setEventLocation(convertToKeysLocation(eventLocation.get(0).children().html()));

        Elements eventName = thisEventElement.getElementsByClass(LISTING_NAME);
        if (eventName.size() > 0)
        {
            thisEventItem.setEventName(eventName.get(0).text());
            if (eventName.get(0).children().hasAttr("href"))
                thisEventItem.setEventURL(eventName.get(0).children().attr("href"));
            else
                thisEventItem.setEventURL("");
        }

        Elements eventContact = thisEventElement.getElementsByClass(LISTING_PHONE);
        thisEventItem.setEventContact(eventContact.size() > 0 ? eventContact.get(0).text() : "No contact phone provided");

        Elements eventDates = thisEventElement.getElementsByClass(LISTING_DATE);
        String[] formattedDates = DateFormatterUtil.formatEventDates(eventDates.text());
        thisEventItem.setEventStartAndEndDate(formattedDates[0], formattedDates[1]);


        return thisEventItem;
    }

    private KeysLocations convertToKeysLocation(String rawHTMLLocation)
    {
        return KeysLocations.convertToEnumLocation(rawHTMLLocation.substring(rawHTMLLocation.lastIndexOf(": ") + 2));
    }


}
