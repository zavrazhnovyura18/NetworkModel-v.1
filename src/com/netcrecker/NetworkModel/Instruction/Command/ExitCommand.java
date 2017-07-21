package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Serialization.NetworkParser;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class ExitCommand implements Command {

    private WorkSpace workSpace;
    private String typeOperation;

    @Override
    public void execute() {
        if(typeOperation.equals("ON SAVE")){
            if(workSpace.getWorkNetwork() != null){
                Network network = workSpace.getWorkNetwork();

                try {
                    NetworkParser parser = new NetworkParser();
                    parser.saveObject(network);
                }catch (JAXBException ex){
                    ex.printStackTrace();
                }
            }
            System.out.println("Operation Success!");
            System.exit(0);
        }else{
            System.out.println("Operation Success!");
            System.exit(0);
        }
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.typeOperation = parameters.get(0);
    }
}
