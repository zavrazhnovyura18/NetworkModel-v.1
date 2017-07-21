package com.netcrecker.NetworkModel.Instruction;

import com.netcrecker.NetworkModel.Exception.InvalidSyntaxCommand;
import com.netcrecker.NetworkModel.Instruction.Command.*;
import com.netcrecker.NetworkModel.Serialization.AdapterInstructions;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Invoker {

    @XmlTransient
    private WorkSpace workSpace;
    public WorkSpace getWorkSpace() {
        return workSpace;
    }
    public void setWorkSpace(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    @XmlJavaTypeAdapter(AdapterInstructions.class)
    private Map<String,Command> commandMap;

    public Invoker() {
        this.workSpace = new WorkSpace();
        this.commandMap = new HashMap<>();
    }


    public Command chooseCommand(String userInput) throws InvalidSyntaxCommand{

        for (Map.Entry<String,Command> command : commandMap.entrySet()) {

            String key = command.getKey();
            Command value = command.getValue();

            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(userInput);
            if(matcher.matches()){
                value.setParameters(parseCommand(userInput),workSpace);
                return value;
            }
        }

        throw new InvalidSyntaxCommand();
    }

    public List<String> parseCommand(String command){
        List<String> parameters = new ArrayList<>();

        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(command);
        while(m.find())
            parameters.add(m.group(1));

        return parameters;
    }
}