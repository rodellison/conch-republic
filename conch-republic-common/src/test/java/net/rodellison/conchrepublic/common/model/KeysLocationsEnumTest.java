package net.rodellison.conchrepublic.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Keys Locations Enum should")
class KeysLocationsEnumTest {

    @Test
    @DisplayName("convert a specific location to string")
    void getLocation() {

        String strLocation = KeysLocations.getLocation(KeysLocations.ALL_FLORIDA_KEYS);
        assertEquals("florida-keys", strLocation);
        strLocation = KeysLocations.getLocation(KeysLocations.KEY_LARGO);
        assertEquals("key-largo", strLocation);
        strLocation = KeysLocations.getLocation(KeysLocations.ISLAMORADA);
        assertEquals("islamorada", strLocation);
        strLocation = KeysLocations.getLocation(KeysLocations.MARATHON);
        assertEquals("marathon", strLocation);
        strLocation = KeysLocations.getLocation(KeysLocations.THE_LOWER_KEYS);
        assertEquals("lower-keys", strLocation);
        strLocation = KeysLocations.getLocation(KeysLocations.KEY_WEST);
        assertEquals("key-west", strLocation);
        strLocation = KeysLocations.getLocation(null);
        assertEquals("florida-keys", strLocation);
    }

}