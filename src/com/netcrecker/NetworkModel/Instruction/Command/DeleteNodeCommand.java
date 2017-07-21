package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Instruction.Command.Command;
import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;
import com.netcrecker.NetworkModel.Network.UpdateLinks;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class DeleteNodeCommand implements Command {

    private WorkSpace workSpace;
    private String id;
    private String networkName;

    @Override
    public void execute() {
        try {
            Network workNetwork = null;
            if(workSpace.getWorkNetwork() != null && workSpace.getWorkNetwork().getName().equals(networkName)){
                workNetwork = workSpace.getWorkNetwork();
            }else{
                workNetwork = new Network(networkName, true);
                workSpace.setWorkNetwork(workNetwork);
            }

            List<PathElement> pathElements = workNetwork.getNodes();
            for (int i = 0; i < pathElements.size(); i++) {
                PathElement pElement = pathElements.get(i);
                String tempId = pElement.getId();
                if(tempId.equals(this.id)){
                    pathElements.remove(pElement);
                    break;
                }
            }
            for (int i = 0; i < workNetwork.getNodes().size(); i++) {
                UpdateLinks updateLinks = (ActiveElement) workNetwork.getNodes().get(i);
                updateLinks.update(this.id);
            }
            System.out.println("Operation Success!");

        }catch (JAXBException ex){
            System.out.println("Сеть с именем " + networkName + " не найдена.");
        }
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.id = parameters.get(0);
        this.networkName = parameters.get(1);
    }
}
