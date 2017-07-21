package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class SelectNodesCommand implements Command {

    private String nameNetwork;
    private String flag;
    private WorkSpace workSpace;

    @Override
    public void execute() {
        try {
            Network network = null;
            if(flag.equals("1")){
                network = new Network(nameNetwork, true);
                workSpace.setWorkNetwork(network);
            }else{
                if(workSpace.getWorkNetwork() != null && workSpace.getWorkNetwork().getName().equals(nameNetwork)){
                    network = workSpace.getWorkNetwork();
                }else {
                    network = new Network(nameNetwork, true);
                    if(workSpace.getWorkNetwork() == null){
                        workSpace.setWorkNetwork(network);
                    }
                }
            }

            List<PathElement> pathElements = network.getNodes();

            for (int i = 0; i < pathElements.size(); i++) {
                System.out.println(pathElements.get(i).getInfo());
            }
            System.out.println("Operation Success!");

        }catch (JAXBException ex){
            System.out.println("Сеть с именем " + nameNetwork + " не найдена.");
        }

    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.nameNetwork = parameters.get(0);
        this.workSpace = workSpace;
        this.flag = parameters.get(1);
    }
}
