package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Exception.InvalidActionException;
import com.netcrecker.NetworkModel.Exception.RouteNotFoundException;
import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.ActiveElement.*;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;
import com.netcrecker.NetworkModel.RouteProvider.RouteProvider;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class AddNodeCommand implements Command {

    private String typeNode;
    private WorkSpace workSpace;
    private String IP;
    private int numOfConnections;
    private float costs;
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

            ActiveElement activeElement = null;
            switch (typeNode) {
                case "PC": activeElement = new PC(this.IP,this.costs,this.networkName);
                    break;
                case "Firewall": activeElement = new Firewall(this.IP,this.numOfConnections,this.costs,this.networkName);
                    break;
                case "Router": activeElement = new Router(this.IP,this.numOfConnections,this.costs,this.networkName);
                    break;
                case "Switch": activeElement = new Switch(this.IP,this.numOfConnections,this.costs,this.networkName);
            }

            workNetwork.setNodes(activeElement);

            System.out.println("Operation Success!");
        }catch (JAXBException ex){
            System.out.println("Сеть с именем " + networkName + " не найдена.");
        }
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.typeNode = parameters.get(0);
        this.IP = parameters.get(1);
        this.numOfConnections = Integer.parseInt(parameters.get(2));
        this.costs = Float.parseFloat(parameters.get(3));
        this.networkName = parameters.get(4);
    }
}
