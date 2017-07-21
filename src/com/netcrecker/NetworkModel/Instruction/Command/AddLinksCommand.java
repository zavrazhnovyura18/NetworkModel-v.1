package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Exception.InvalidActionException;
import com.netcrecker.NetworkModel.Exception.InvalidConnectionException;
import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.ActiveElement.*;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PassiveElement.Cable;
import com.netcrecker.NetworkModel.Network.PassiveElement.Hub;
import com.netcrecker.NetworkModel.Network.PathElement;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class AddLinksCommand implements Command{

    private WorkSpace workSpace;
    private String networkName;
    private String typeNode;
    private Float channelCapacity;
    private int numConn;
    private List<String> connectNodes;

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

            List<ActiveElement> needElement = new ArrayList<>();
            for (int i = 0; i < workNetwork.getNodes().size(); i++) {

                PathElement pathElement = workNetwork.getNodes().get(i);

                for (int j = 0; j < connectNodes.size(); j++) {
                    if(pathElement.getId().equals(connectNodes.get(j))){
                        needElement.add((ActiveElement) pathElement);
                        break;
                    }
                }
            }

            boolean isPossible = true;
            for (int i = 0; i < needElement.size(); i++) {
                isPossible = needElement.get(i).checkNumOfConnection();
            }

            if(!isPossible){
                throw new InvalidActionException("В один из узлов нельзя больше подключить элементы.");
            }


            if (typeNode.equals("Cable")){

                Cable cable1 = new Cable(this.channelCapacity,this.networkName,needElement.get(0));
                Cable cable2 = new Cable(this.channelCapacity,this.networkName,needElement.get(1));

                needElement.get(0).setConnections(cable2);
                needElement.get(1).setConnections(cable1);

            }else{

                for (int i = 0; i < needElement.size(); i++) {

                    ActiveElement activeElement = needElement.get(i);
                    Hub hub = new Hub(this.channelCapacity, this.networkName,this.numConn);

                    for (int j = 0; j < needElement.size(); j++) {
                        ActiveElement aE = needElement.get(j);
                        if(i != j){
                            hub.setConnections(aE);
                        }
                    }

                    activeElement.setConnections(hub);
                }

            }

            System.out.println("Operation Success!");
        }catch (JAXBException ex){
            System.out.println("Сеть с именем " + networkName + " не найдена.");
        }catch (InvalidActionException | InvalidConnectionException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.networkName = parameters.get(0);
        this.typeNode = parameters.get(1);
        this.channelCapacity = Float.parseFloat(parameters.get(2));
        this.connectNodes = new ArrayList<>();

        if(typeNode.equals("Cable")){
            for (int i = 3; i < parameters.size(); i++) {
                connectNodes.add(parameters.get(i));
            }
        }else{
            this.numConn = Integer.parseInt(parameters.get(3));
            String[] mas = parameters.get(4).split(",");
            for (int i = 0; i < mas.length; i++) {
                if(!mas[i].isEmpty()){
                    connectNodes.add(mas[i]);
                }
            }
        }

    }
}
