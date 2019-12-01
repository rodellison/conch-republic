package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import com.rodellison.conchrepublic.backend.utils.DataFetchUtilStub;  //stub containing some actual return data
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Web Events Formatting Manager should")
public class WebEventsFormattingManagerShould {

    private static final Logger log = LogManager.getLogger(WebEventsFormattingManagerShould.class);

    @Test
    @DisplayName("convert the raw html to an List<EventItem>")
    void convertRawHTMLToEventList() {

        WebEventsFormattingManager myWebEventsFormattingManager = new WebEventsFormattingManager();
        ArrayList<String> rawHTMLDataTest = new ArrayList<>();
        rawHTMLDataTest.add(DataFetchUtilStub.getTestHTMLString());
        List<EventItem> theEventListData = myWebEventsFormattingManager.convertRawHTMLToEventList(rawHTMLDataTest);
    //    theEventListData.forEach(log::info);
        assertEquals(12, theEventListData.size());

    }

}