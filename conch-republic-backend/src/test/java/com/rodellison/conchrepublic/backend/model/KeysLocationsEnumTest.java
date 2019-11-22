package com.rodellison.conchrepublic.backend.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Keys Locations Enum should")
class KeysLocationsEnumTest {

    @Test
    @DisplayName("convert a specific location to string")
    void getLocation() {

         String strLocation = KeysLocations.getLocation(KeysLocations.ALL_FLORIDA_KEYS);
         assertEquals("florida-keys", strLocation);

    }

}