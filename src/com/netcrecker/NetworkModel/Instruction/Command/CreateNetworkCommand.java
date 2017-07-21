package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Instruction.Command.Command;
import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.Network;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class CreateNetworkCommand implements Command {

    private String nameNetwork;

    private WorkSpace workSpace;

    @Override
    public void execute() {
        Network network = new Network(nameNetwork);
        workSpace.setWorkNetwork(network);
        System.out.println("Operation Success!");
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.nameNetwork = parameters.get(0);
        this.workSpace = workSpace;
    }
}
