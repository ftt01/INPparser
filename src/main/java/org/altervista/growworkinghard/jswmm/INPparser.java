/*
 * Copyright 2017 gwh-2b4 Daniele Dalla Torre
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.altervista.growworkinghard.jswmm;

import com.github.geoframecomponents.jswmm.dataStructure.SWMMobject;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

/**
 * @author ftt01 <dallatorre.daniele@gmail.com></>
 * @version 0.1
 */

public class INPparser {

    public class INPfile {
        INPConfiguration configuration;
        FileBasedConfigurationBuilder<INPConfiguration> builder;

        INPfile(INPConfiguration configuration, FileBasedConfigurationBuilder<INPConfiguration> builder) {
            this.configuration = configuration;
            this.builder = builder;
        }

    }

    private Parameters params;
    private Map<String, INPfile> loadedFiles;
    private Map<String, List<String>> sectionLabels = new HashMap<>();
    private Map<String, List<String>> sectionElements = new HashMap<>();

    /**
     * Basic constructor
     */
    public INPparser() {
        this.loadedFiles = new LinkedHashMap<>();
        this.params = new Parameters();
    }

    /**
     * Build the configuration file.
     *
     * @param file
     * @throws ConfigurationException
     */
    public void load(String file) throws ConfigurationException {

        FileBasedConfigurationBuilder<INPConfiguration> builder;
        builder = new FileBasedConfigurationBuilder<>(INPConfiguration.class)
                .configure(params.ini()
                        .setFileName(file));
        INPConfiguration config = builder.getConfiguration();

        loadedFiles.put(file, new INPfile(config, builder));
    }

    /**
     * Getter that return the selected data.
     *
     * @param file where to read the data
     * @param section where the data is located
     * @param nameOfObject Id of the object
     * @param propertyColumn
     * @return
     */
    public String getProperty(String file, String section, String nameOfObject, int propertyColumn){

        String tmpStr = section + "." + nameOfObject;

        String body = (String) loadedFiles.get(file).configuration.getProperty(tmpStr);
        List<String> splittedBody = new ArrayList<>(Arrays.asList(body.split("\\s+")));

        System.out.println(splittedBody.size());

        return splittedBody.get(propertyColumn-1);
    }

    private String cutCommentCharachter(String nameOfObject) {
        if (nameOfObject.startsWith(";")){
            return cutCommentCharachter(nameOfObject.substring(1));
        }
        return nameOfObject;
    }

    public void setProperty(String file, String section, String element, int propertyColumn, String value)
            throws ConfigurationException {

        String tmpStr = section + "." + element;

        String body = (String) loadedFiles.get(file).configuration.getProperty(tmpStr);
        List<String> splittedBody = new ArrayList<>(Arrays.asList(body.split("\\s+")));

        splittedBody.set(propertyColumn-1, value);

        StringBuilder valuesBuilder = new StringBuilder();

        for(int i=0; i<splittedBody.size(); i++){
            valuesBuilder.append(String.format("%1$-17s", splittedBody.get(i)));
        }

        loadedFiles.get(file).configuration.setProperty(tmpStr, valuesBuilder.toString());
    }

    /**
     * Save the file
     */
    public void save(String file) throws ConfigurationException {
        loadedFiles.get(file).builder.save();
    }

    /**
     * Save the file with another name, if the file exists it return an IOException
     */
    public void saveAs(String file, String newFile) throws IOException, ConfigurationException {

        loadSWMMTemplate(newFile);
        loadedFiles.get(newFile).configuration.copy(loadedFiles.get(file).configuration);
        loadedFiles.get(newFile).builder.save();
    }

    private void loadSWMMTemplate(String newFile) throws ConfigurationException, IOException {
        createFile(newFile);
        writeSWMMTemplate(newFile);
        load(newFile);
    }

    private void writeSWMMTemplate(String newFile) throws IOException {

        PrintWriter printWriter = new PrintWriter(newFile);

        printWriter.println("[TITLE]" + "\n");
        printWriter.println("[OPTIONS]" + "\n");
        printWriter.println("[REPORT]" + "\n");
        printWriter.println("[FILES]" + "\n");
        printWriter.println("[RAINGAGES]" + "\n");
        printWriter.println("[EVAPORATION]" + "\n");
        printWriter.println("[TEMPERATURE]" + "\n");
        printWriter.println("[ADJUSTMENTS]" + "\n");
        printWriter.println("[SUBCATCHMENTS]" + "\n");
        printWriter.println("[SUBAREAS]" + "\n");
        printWriter.println("[INFILTRATION]" + "\n");
        printWriter.println("[LID_CONTROLS]" + "\n");
        printWriter.println("[LID_USAGE]" + "\n");
        printWriter.println("[OPTIONS]" + "\n");
        printWriter.println("[AQUIFERS]" + "\n");
        printWriter.println("[GROUNDWATER]" + "\n");
        printWriter.println("[GWF]" + "\n");
        printWriter.println("[SNOWPACKS]" + "\n");
        printWriter.println("[JUNCTIONS]" + "\n");
        printWriter.println("[OUTFALLS]" + "\n");
        printWriter.println("[DIVIDERS]" + "\n");
        printWriter.println("[STORAGE]" + "\n");
        printWriter.println("[CONDUITS]" + "\n");
        printWriter.println("[PUMPS]" + "\n");
        printWriter.println("[ORIFICES]" + "\n");
        printWriter.println("[WEIRS]" + "\n");
        printWriter.println("[OUTLETS]" + "\n");
        printWriter.println("[XSECTIONS]" + "\n");
        printWriter.println("[TRANSECTS]" + "\n");
        printWriter.println("[LOSSES]" + "\n");
        printWriter.println("[CONTROLS]" + "\n");
        printWriter.println("[POLLUTANTS]" + "\n");
        printWriter.println("[LANDUSES]" + "\n");
        printWriter.println("[COVERAGES]" + "\n");
        printWriter.println("[LOADINGS]" + "\n");
        printWriter.println("[BUILDUP]" + "\n");
        printWriter.println("[WASHOFF]" + "\n");
        printWriter.println("[TREATMENT]" + "\n");
        printWriter.println("[INFLOWS]" + "\n");
        printWriter.println("[DWF]" + "\n");
        printWriter.println("[RDII]" + "\n");
        printWriter.println("[HYDROGRAPHS]" + "\n");
        printWriter.println("[CURVES]" + "\n");
        printWriter.println("[TIMESERIES]" + "\n");
        printWriter.println("[PATTERNS]" + "\n");

        printWriter.close();
    }

    private void createFile(String newFile) throws IOException {

        File fileObject = new File(newFile);

        if (!fileObject.createNewFile()) {
            throw new FileAlreadyExistsException(newFile);
        }
    }

    private void readLines(String nameOfFile){
        try{
            FileInputStream fstream = new FileInputStream(nameOfFile);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            boolean newSection = false;
            //Read File Line By Line
            String currentSection = null;
            List<String> listElements = new ArrayList<>();
            while ((strLine = br.readLine()) != null)   {
                if (!strLine.isEmpty()) {
                    if (strLine.startsWith("[")) {
                        newSection = true;
                        //Copy previous section elements before overwrite currentSection
                        if (currentSection != null) {
                            sectionElements.put(currentSection, listElements);
                        }
                        currentSection = strLine.substring(1,strLine.length()-1);
                    }
                    else {
                        if (strLine.startsWith(";")) {
                            if (newSection) {
                                strLine = cutCommentCharachter(strLine);

                                List<String> tmpArray = Arrays.asList(strLine.split("\\s+"));
                                sectionLabels.put(currentSection, tmpArray);
                                newSection = false;
                            }
                        }
                        else {
                            List<String> tmpArray = Arrays.asList(strLine.split("\\s+"));
                            if (currentSection.equals("TITLE")){
                                String str = String.join(" ", tmpArray);
                                listElements.add(str);
                            }
                            else{
                                listElements.add(tmpArray.get(0));
                            }
                        }
                    }
                }
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void parseSWMMinp(SWMMobject dataStr) {

    }
}