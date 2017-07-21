package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Exception.InvalidActionException;
import com.netcrecker.NetworkModel.Exception.RouteNotFoundException;
import com.netcrecker.NetworkModel.Instruction.Command.Command;
import com.netcrecker.NetworkModel.Instruction.WorkSpace;
import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;
import com.netcrecker.NetworkModel.Network.UpdateLinks;
import com.netcrecker.NetworkModel.RouteProvider.RouteProvider;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
public class FindRouteCommand implements Command {

    private String nameNetwork;
    private WorkSpace workSpace;
    private String startID;
    private String endID;
    private String provider;

    @Override
    public void execute() {
        try {
            Network workNetwork = null;
            if(workSpace.getWorkNetwork() != null && workSpace.getWorkNetwork().getName().equals(nameNetwork)){
                workNetwork = workSpace.getWorkNetwork();
            }else{
                workNetwork = new Network(nameNetwork, true);
                workSpace.setWorkNetwork(workNetwork);
            }

            List<Class> providersName = workNetwork.getProviders();
            RouteProvider findProviders = null;
            for (int i = 0; i < providersName.size(); i++) {

                try {
                    RouteProvider routeProvider = (RouteProvider) providersName.get(i).newInstance();

                    List<String> algorithms = routeProvider.getAlgorithm();
                    if(algorithms != null){
                        for (int j = 0; j < algorithms.size(); j++) {
                            if(algorithms.get(j).equals(this.provider)){
                                findProviders = routeProvider;
                            }
                        }
                    }

                }catch (InstantiationException | IllegalAccessException ex) {
                    System.out.println("Произошла ошибка при динамической подгрузке провайдера.");
                }

            }

            if(findProviders != null){
                Integer firstId = Integer.parseInt(this.startID);
                Integer secondId = Integer.parseInt(this.endID);

                List<PathElement> route = findProviders.getRoute(firstId,secondId,workNetwork,this.provider);

                for (int i = 0; i < route.size(); i++) {
                    String end = null;
                    if(i == route.size() - 1){
                        end = " ....";
                    }else{
                        end = " - ";
                    }

                    System.out.print(route.get(i).getId() + end);
                }
                System.out.println();
                System.out.println("Operation Success!");

            }else{
                throw new InvalidActionException("Провайдера " + this.provider + " не существует. Проверьте внимательно.");
            }


        }catch (JAXBException ex){
            System.out.println("Сеть с именем " + nameNetwork + " не найдена.");
        }catch (InvalidActionException ex){
            ex.printStackTrace();
        }catch (RouteNotFoundException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void setParameters(List<String> parameters, WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.nameNetwork = parameters.get(0);
        this.startID = parameters.get(1);
        this.endID = parameters.get(2);
        this.provider = parameters.get(3);
    }
}
