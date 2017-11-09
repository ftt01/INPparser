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

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ftt01 <dallatorre.daniele@gmail.com></>
 * @version 0.1
 */

public class INPparser {

    private Parameters params = new Parameters();

    private Map<String, FileBasedConfigurationBuilder<INPConfiguration>> buildedNodes = new LinkedHashMap<>();
    private Map<String, INPConfiguration> loadedFiles = new LinkedHashMap<>();

    /**
     * Basic constructor
     */
    public INPparser() {}

    /**
     * Build the ImmutableNode from file string.
     *
     * @return the builded configuration
     * @throws ConfigurationException
     */
    public void load(String file)
            throws ConfigurationException {

        FileBasedConfigurationBuilder<INPConfiguration> builder;
        builder = new FileBasedConfigurationBuilder<>(INPConfiguration.class)
                .configure(params.ini()
                        .setFileName(file));
        INPConfiguration config = builder.getConfiguration();

        buildedNodes.put(file, builder);
        loadedFiles.put(file, config);
    }

    /**
     * Build the ImmutableNode from File type.
     *
     * @return the builded configuration
     * @throws ConfigurationException
     */
    public void load(File file)
            throws ConfigurationException {

        load(file.toString());
    }

    /**
     * Save the file
     */
    public void save(String file) throws ConfigurationException {
        buildedNodes.get(file).save();
    }

    /**
     * Save the file with another name
     */
    public void save(String file, String newFile) throws ConfigurationException {
        if(!buildedNodes.containsKey(file)){
            loadINPTemplate(newFile);
            loadedFiles.get(newFile).copy(loadedFiles.get(file));
        }
        buildedNodes.get(file).save();
    }

    private void loadINPTemplate(String newFile) throws ConfigurationException {
        FileBasedConfigurationBuilder<INPConfiguration> builder;
        builder = new FileBasedConfigurationBuilder<>(INPConfiguration.class)
                .configure(params.ini());
        INPConfiguration config = builder.getConfiguration();

        buildedNodes.put(newFile, builder);
        loadedFiles.put(newFile, config);
    }

    public String getProperty(String file, String section, String element, int propertyColumn){

        String tmpStr = section + "." + element;

        String body = (String) loadedFiles.get(file).getProperty(tmpStr);
        List<String> splittedBody = new ArrayList<>(Arrays.asList(body.split("\\s+")));
        return splittedBody.get(propertyColumn-1);
    }

    public void setProperty(String file, String section, String element, String value) throws ConfigurationException {

        String tmpStr = section + "." + element;
        loadedFiles.get(file).setProperty(tmpStr, value);
    }
}