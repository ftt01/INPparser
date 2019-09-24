package org.altervista.growworkinghard.jswmm;

import com.github.geoframecomponents.jswmm.dataStructure.SWMMobject;
import org.apache.commons.configuration2.ex.ConfigurationException;
import java.io.IOException;

public class testMain {
    public static void main(String[]args) throws ConfigurationException, IOException {

        SWMMobject dataStr;
        INPparser object1;

        dataStr = new SWMMobject();
        object1 = new INPparser();
        object1.load("./data/test/2D.inp");
        object1.parseSWMMinp(dataStr);



        //object1.readLines("./data/test/2D.inp");
        //EXAMPLE GET DATA FROM EXISTING FILE
        //object1.load("./data/test/2D.inp");

        //String test2 = object1.getProperty("./data/test/2D.inp", "RAINGAGES", ";;RG_ID", 2);
        //System.out.println(test2);

        //String test3 = object1.getProperty("2D.inp", "OPTIONS", "FLOW_UNITS", 1);
        //System.out.println(test3);

        //object1.setProperty("2D.inp", "SUBCATCHMENTS", "r101a_2D", 5, "26.0000");

        //object1.save("2D.inp");
        //object1.saveAs("2D.inp", "5D.inp");

        //EXAMPLE SET DATA TO NEW EMPTY FILE
        //TODO SWMMdataStructure
        //object1.fill("3D.inp");




        //object1.load("3D.inp");

        //String test2 = object1.getProperty("2D.inp", "SUBCATCHMENTS", "r100a_2D", 5);
        //System.out.println(test2);

        //object1.load("2D.inp_");
        //String test1 = object1.getProperty("2D.inp_", "OPTIONS", "INFILTRATION", 1);
        //System.out.println(test1);

        //String test3 = object1.getProperty("2D.inp", "OPTIONS", "INFILTRATION", 1);
        //System.out.println(test3);

        //String test3 = object1.getProperty("2D.inp", "OPTIONS", "INFILTRATION", 1);
        //System.out.println(test3);
        //object1.setProperty("2D.inp", "SUBCATCHMENTS", "r100a_2D", 5, "26.0000");

        //object1.save("2D.inp");
        //object1.save("3D.inp");
        //object1.saveAs("2D.inp", "4D.inp");

        //object1.read("2D.inp", "OPTIONS", "key", "value");
        //object1.update("2D.inp", "OPTIONS", "key", "value");
        //object1.save("2D.inp");

        //INPConfiguration file2 = (INPConfiguration) file1.clone();

        //LinkedHashMap<String, List<String>> testRead1;
        //testRead1 = object1.reader(file1, "OPTIONS");

        //LinkedHashMap<String, List<String>> testRead2;
        //testRead2 = object1.reader(file2, "OPTIONS");

        //file1.update("OPTIONS", "KEY", "VALUE");


        //System.out.println(file1.getProperties("FLOW_UNITS"));

        //System.out.println(file1.getSections());
        //System.out.println(file1.getSection("OPTIONS"));
        //file1.setProperty("OPTIONS.INFILTRATION", "TEST_INFILTRATION_2");

        //file1builder.save();









        /*INPparser testReader = new INPparser();

        LinkedHashMap<String, List<String>> testRead1;
        LinkedHashMap<String, List<String>> testRead2;

        testRead1 = testReader.reader("2D.inp", "OPTIONS");
        testRead2 = testReader.reader("RAINGAGES");

        INPparser testWriter = new INPparser("template.inp", "2D_new.inp", false);

        testWriter.update(testRead1,"OPTIONS");
        testWriter.writer(testRead2,"RAINGAGES");

        testWriter.write("2D_new.inp");
        testWriter.write("2D_new2.inp");*/

    }
}
