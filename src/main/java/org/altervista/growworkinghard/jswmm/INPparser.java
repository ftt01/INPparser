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

    private String file;

    private boolean override = false;

    private FileBasedConfigurationBuilder<INPConfiguration> writeBuilder;

    private File fileObject;

    private Parameters params = new Parameters();

    private String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    public INPparser(String file) throws IOException {
        this.file = file;
        this.fileObject = new File(file);
        writeBuilder = buildNode();
    }

    public INPparser(String templateFile, String file, boolean override) throws IOException {
        this.file = file;
        this.override = override;
        this.fileObject = new File(file);
        File templateFileObject = new File(templateFile);
        writeBuilder = buildNode(templateFileObject);
    }

    public String getFile(){
        return file;
    }

    public void setFile(String file) {
        this.file = file;
        this.fileObject = new File(file);
    }

    public String getSection(){
        return file;
    }

    public void setSection(String file){
        this.file = file;
    }

    public LinkedHashMap<String, List<String>> reader(String section)
            throws ConfigurationException, IOException {

        FileBasedConfigurationBuilder<INPConfiguration> readBuilder;
        readBuilder = buildNode();

        INPConfiguration config = readBuilder.getConfiguration();
        HierarchicalConfiguration<ImmutableNode> sectionNode = config.configurationAt(section);

        Iterator<String> keysIterator = sectionNode.getKeys();

        //while (keysIterator.hasNext()) System.out.println(keysIterator.next());

        LinkedHashMap<String, List<String>> table = new LinkedHashMap<>();

        while (keysIterator.hasNext()) {
            String tmpKey = keysIterator.next();

            String body = sectionNode.getString(tmpKey);
            List<String> splittedBody = new ArrayList<>(Arrays.asList(body.split("\\s+")));

            table.put(tmpKey, splittedBody);
        }

        return table;
    }

    public void writer(LinkedHashMap<String, List<String>> table, String section)
            throws ConfigurationException, IOException {

        INPConfiguration config = writeBuilder.getConfiguration();
        HierarchicalConfiguration<ImmutableNode> sectionNode = config.configurationAt(section, true);

        Set<String> keys = table.keySet();
        Iterator<String> keysIterator = keys.iterator();

        while (keysIterator.hasNext()) {
            String tmpKey = keysIterator.next();
            String tableValues = String.join("\t\t", table.get(tmpKey));

            sectionNode.setProperty(tmpKey, tableValues);
        }
    }

    public void saveWroteData() throws ConfigurationException {
        writeBuilder.save();
    }

    private FileBasedConfigurationBuilder<INPConfiguration> buildNode(File templateFile)
            throws IOException {

        if(override){
            while(!fileObject.createNewFile()) {
                fileObject.delete();
            }
        }
        else {
            while(!fileObject.createNewFile()) {
                String fileNameWithOutExt = FilenameUtils.removeExtension(file);
                String extensionFile = FilenameUtils.getExtension(file);

                String oldFileName = fileNameWithOutExt + "_mod_" + timeStamp + "." + extensionFile;
                File oldFileObject = new File(oldFileName);
                fileObject.renameTo(oldFileObject);
            }
        }

        if (!templateFile.equals(fileObject)){
            FileUtils.copyFile(templateFile, fileObject);
        }

        return new FileBasedConfigurationBuilder<>(INPConfiguration.class)
                .configure(params.ini()
                        .setFileName(file));
    }

    private FileBasedConfigurationBuilder<INPConfiguration> buildNode() throws IOException {

        return new FileBasedConfigurationBuilder<>(INPConfiguration.class)
                    .configure(params.ini()
                            .setFileName(file));
    }
}