package com.netcrecker.NetworkModel.Instruction;

import com.netcrecker.NetworkModel.Exception.InvalidSyntaxCommand;
import com.netcrecker.NetworkModel.Instruction.Command.Command;
import com.netcrecker.NetworkModel.Serialization.NetworkParser;

import javax.xml.bind.JAXBException;


public class Facade {

    private Invoker invoker;

    public Facade() {
        NetworkParser parser = new NetworkParser();
        try {
            this.invoker = (Invoker) parser.getOtherObject("assets/Important/Commands", Invoker.class);
        }catch (JAXBException ex){
            ex.printStackTrace();
        }
    }

    public void doAction(String textCommand){
        try {
            Command result = invoker.chooseCommand(textCommand);
            result.execute();
        }catch (InvalidSyntaxCommand ex) {
            ex.printStackTrace();
        }
    }
}
