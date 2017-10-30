package org.altervista.growworkinghard.jswmm;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.ImmutableNode;

import java.util.*;

public class INPparser {
    private String file;
    private String section;

    public INPparser(String file, String section) throws ConfigurationException {
        this.file = file;
        this.section = section;
    }

    public String getFile(){
        return file;
    }

    public void setFile(String file){
        this.file = file;
    }

    public String getSection(){
        return file;
    }

    public void setSection(String file){
        this.file = file;
    }

    public LinkedHashMap<String, List<String>> reader() throws ConfigurationException {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<INPConfiguration> builder =
                new FileBasedConfigurationBuilder<INPConfiguration>(INPConfiguration.class)
                        .configure(params.ini()
                                .setFileName(file));

        INPConfiguration config = builder.getConfiguration();
        HierarchicalConfiguration<ImmutableNode> sectionNode = config.configurationAt(section);

        Iterator<String> keysIterator = sectionNode.getKeys();

        LinkedHashMap<String, List<String>> table = new LinkedHashMap<>();

        while (keysIterator.hasNext()) {
            String tmpKey = keysIterator.next();
            System.out.print(tmpKey);
            String body = sectionNode.getString(tmpKey);
            System.out.print(body);

            List<String> splittedBody = new ArrayList<>(Arrays.asList(body.split("\\s+")));

            table.put(tmpKey, splittedBody);
            for (Map.Entry<String, List<String>> entry : table.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());
            }

            //for(int i=0; i<values.length; i++) {
            //System.out.println("MONA" + values[i]);

            //System.out.println(body);
            // table.put(values[i], )
            //}

            //table.put(sectionNode.getString(keys.next()), values);

            //for (String name : table.keySet()){
            //  for(int i=0; i<name.length(); i++) {
            //    System.out.println(name + " " + table.get(name)[i]);
            // }
            //}
            //String line = sectionNode.getString(keys.next());
            //System.out.println(line);
            //System.out.println(table.keySet());
        }

        //System.out.println(name + " " + value);

        //String[] words = line.split("\\s+");
        //for (int i=0; i<words.length; i++){
        //System.out.println(words.length);
        //    System.out.println(words[i]);
        //}

        //for (Map.Entry<String, List<String>> entry : table.entrySet()) {
        //    System.out.println(entry.getKey()+" : "+entry.getValue());
        //}

        return table;
    }
}