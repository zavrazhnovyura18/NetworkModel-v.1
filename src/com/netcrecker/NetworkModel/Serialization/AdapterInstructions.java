package com.netcrecker.NetworkModel.Serialization;

import com.netcrecker.NetworkModel.Instruction.Command.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdapterInstructions extends XmlAdapter<AdapterInstructions.Adapted, Map<String, Command>> {

    @Override
    public Map<String, Command> unmarshal(AdapterInstructions.Adapted v) throws Exception {
        if(null == v) {
            return null;
        }
        Map<String, Command> map = new HashMap<>();
        for(Entry entry : v.entry) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    @Override
    public AdapterInstructions.Adapted marshal(Map<String, Command> v) throws Exception {
        if(null == v) {
            return null;
        }
        Adapted adapted = new Adapted();
        for(Map.Entry<String,Command> entry : v.entrySet()) {
            adapted.entry.add(new Entry(entry.getKey(), entry.getValue()));
        }
        return adapted;
    }

    public static class Adapted {
        public List<Entry> entry = new ArrayList<Entry>();
    }

    public static class Entry {
        public Entry() {
        }

        public Entry(String key, Command value) {
            this.key = key;
            this.value = value;
        }
        @XmlElement(name = "TextCommand")
        public String key;

        @XmlElements({
                @XmlElement(name = "AddLinks",type = AddLinksCommand.class),
                @XmlElement(name = "AddNode",type = AddNodeCommand.class),
                @XmlElement(name = "CreateNetwork",type = CreateNetworkCommand.class),
                @XmlElement(name = "DeleteNode",type = DeleteNodeCommand.class),
                @XmlElement(name = "Exit",type = ExitCommand.class),
                @XmlElement(name = "FindRoute",type = FindRouteCommand.class),
                @XmlElement(name = "SelectNodes",type = SelectNodesCommand.class)
        })
        public Command value;
    }
}
