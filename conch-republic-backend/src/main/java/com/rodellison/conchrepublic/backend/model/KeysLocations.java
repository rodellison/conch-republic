package com.rodellison.conchrepublic.backend.model;

public enum KeysLocations {

    ALL_FLORIDA_KEYS,
    KEY_LARGO,
    ISLAMORADA,
    MARATHON,
    THE_LOWER_KEYS,
    KEY_WEST;

    public static final String getLocation(KeysLocations item)
    {
        String returnVal;

        if (item == null)
            return "florida-keys";

        switch (item)
        {
            case ALL_FLORIDA_KEYS: {
                returnVal = "florida-keys";
                break;
            }
            case KEY_LARGO: {
                returnVal = "key-largo";
                break;
            }
            case ISLAMORADA: {
                returnVal = "islamorada";
                break;
            }
            case MARATHON: {
                returnVal = "marathon";
                break;
            }
            case THE_LOWER_KEYS: {
                returnVal = "lower-keys";
                break;
            }
            case KEY_WEST: {
                returnVal = "key-west";
                break;
            }
            default:
                returnVal = "florida-keys";

        }
        return returnVal;


    };

}
