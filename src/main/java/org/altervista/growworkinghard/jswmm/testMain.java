package org.altervista.growworkinghard.jswmm;

import java.util.*;

import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;

public class testMain {
    public static void main(String[]args) throws ConfigurationException {
        INPparser testReader = new INPparser("2D.inp");

        LinkedHashMap<String, List<String>> testRead;

        testRead = testReader.reader("SUBCATCHMENTS");

        testReader.writer(testRead,"SUBCATCHMENTS");
    }
}
