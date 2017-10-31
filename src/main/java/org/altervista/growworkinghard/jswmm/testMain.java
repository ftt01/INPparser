package org.altervista.growworkinghard.jswmm;

import java.io.IOException;
import java.util.*;

import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;

public class testMain {
    public static void main(String[]args) throws ConfigurationException, IOException {
        INPparser testReader = new INPparser("tp_1.inp");
        INPparser testWriter = new INPparser("template.inp", "tp_5.inp");

        LinkedHashMap<String, List<String>> testRead;

        testRead = testReader.reader("OPTIONS");

        testWriter.writer(testRead,"OPTIONS");
        testWriter.writer(testRead,"TITLE");

        testWriter.saveWroteData();
    }
}
