package com.rodellison.conchrepublic.backend.managers;

import com.rodellison.conchrepublic.backend.model.EventItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class DataBaseManager implements DataBaseManagerInterface {

    private static final Logger log = LogManager.getLogger(DataBaseManager.class);
    private DataBaseManagerInterface dbInterface;

    public DataBaseManager(DataBaseManagerInterface theDBInterfaceToUse) {
        this.dbInterface = theDBInterfaceToUse;
    }

    /**
     * insertEventDataIntoDB is used to interface with a dynamoDB table for the purposes of inserting EventInfo Data
     *
     * @param theEventList EventItem object containing elements that will be inserted into the DynamoDB table
     * @return true/false DB insert success
     */
    @Override
    public Boolean insertEventDataIntoDB(ArrayList<EventItem> theEventList) {

        return dbInterface.insertEventDataIntoDB(theEventList);
    }

}
