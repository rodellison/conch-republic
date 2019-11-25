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

    public static final KeysLocations convertToEnumLocation(String item)
    {
        KeysLocations returnVal;

        if (item.isEmpty())
            return KeysLocations.ALL_FLORIDA_KEYS;

        switch (item.toLowerCase())
        {
            case "florida keys": {
                returnVal = KeysLocations.ALL_FLORIDA_KEYS;
                break;
            }
            case "key largo": {
                returnVal = KeysLocations.KEY_LARGO;
                break;
            }
            case "islamorada": {
                returnVal = KeysLocations.ISLAMORADA;
                break;
            }
            case "marathon": {
                returnVal = KeysLocations.MARATHON;
                break;
            }
            case "the lower keys": {
                returnVal = KeysLocations.THE_LOWER_KEYS;
                break;
            }
            case "key west": {
                returnVal = KeysLocations.KEY_WEST;
                break;
            }
            default:
                returnVal = KeysLocations.ALL_FLORIDA_KEYS;

        }
        return returnVal;


    };
}
