package com.netcrecker.NetworkModel.Network.ActiveElement;

import com.netcrecker.NetworkModel.Exception.InvalidConnectionException;
import com.netcrecker.NetworkModel.Network.*;
import com.netcrecker.NetworkModel.Network.PassiveElement.PassiveElement;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlType
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({PC.class,Firewall.class,Router.class,Switch.class})
public abstract class ActiveElement implements PathElement, UpdateLinks{

    @XmlAttribute
    private String id;
    @Override
    public String getId() {
        return this.id;
    }

    @XmlElement
    private String IP;
    public String getIP() {
        return IP;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }

    @XmlElement
    private int numOfConnections;
    public int getNumOfConnections() {
        return numOfConnections;
    }
    public void setNumOfConnections(int numOfConnections) {
        this.numOfConnections = numOfConnections;
    }
    public boolean checkNumOfConnection(){
        return ((connections.size() + 1) > numOfConnections)? false : true;
    }

    @XmlElement
    private double costs;
    public double getCosts() {
        return costs;
    }
    public void setCosts(double costs) {
        this.costs = costs;
    }

    public double getTimeDelay(){
        double timeDelay = 0;
        for (PassiveElement pE : connections) {
            timeDelay += pE.getChannelCapacity();
        }
        return timeDelay;
    }

    @XmlElementWrapper(name = "Connections")
    @XmlElement(name = "connect")
    private List<PassiveElement> connections;
    @Override
    public List<ActiveElement> getConnections() {
        List<ActiveElement> elements = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            for (int j = 0; j < connections.get(i).getConnections().size(); j++) {
                elements.add(connections.get(i).getConnections().get(j));
            }
        }
        return elements;
    }
    public List<PassiveElement> getPassiveElement(){
        return this.connections;
    }
    public void setConnections(PassiveElement ... passiveElements) throws InvalidConnectionException {
        for (int i = 0; i < passiveElements.length; i++) {
            if((connections.size() + 1) > numOfConnections){
                throw new InvalidConnectionException("Ну это уже наглость, ты пытаешься впихнуть невпихуемое, " +
                        "ты не видишь что-ли, портов уже нет");
            }
            this.connections.add(passiveElements[i]);
        }
    }

    @XmlAttribute
    private String networkName;
    @Override
    public String getNetworkName() {
        return this.networkName;
    }

    public ActiveElement(String IP, int numOfConnections, double costs, String networkName){
        this.IP = IP;
        this.numOfConnections = numOfConnections;
        this.costs = costs;
        this.connections = new ArrayList<>();
        this.id = (int) (Math.random() * Integer.MAX_VALUE) + "";
        this.networkName = networkName;
    }

    public ActiveElement() {
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        builder.append("{\n");
        builder.append(" id = ").append(id).append("\n");
        builder.append(" IP = ").append(IP).append("\n");
        builder.append(" numOfConnections = ").append(numOfConnections).append("\n");
        builder.append(" Connect list:[\n");
        for (int i = 0; i < connections.size(); i++) {
            for (int j = 0; j < connections.get(i).getConnections().size(); j++) {
                builder.append("   id = ");
                builder.append(connections.get(i).getConnections().get(j).getId());
                builder.append("\n");
            }
            //builder.append(connections.get(i).getInfo());
            //builder.append("\n");
        }
        builder.append(" ]\n");
        builder.append("}");
        return builder.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActiveElement)) return false;

        ActiveElement that = (ActiveElement) o;

        if (numOfConnections != that.numOfConnections) return false;
        if (Double.compare(that.costs, costs) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (IP != null ? !IP.equals(that.IP) : that.IP != null) return false;
        if (connections != null ? !connections.equals(that.connections) : that.connections != null) return false;
        return networkName != null ? networkName.equals(that.networkName) : that.networkName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (IP != null ? IP.hashCode() : 0);
        result = 31 * result + numOfConnections;
        temp = Double.doubleToLongBits(costs);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (connections != null ? connections.hashCode() : 0);
        result = 31 * result + (networkName != null ? networkName.hashCode() : 0);
        return result;
    }

    @Override
    public void update(String id) {
        for (int i = 0; i < connections.size(); i++) {
            PassiveElement pE = connections.get(i);
            List<ActiveElement> activeElements = pE.getConnections();
            for (int j = 0; j < activeElements.size(); j++) {
                PathElement aE = activeElements.get(j);
                if(aE.getId().equals(id)){
                    activeElements.remove(aE);
                }
            }
            if(activeElements.size() == 0){
                connections.remove(pE);
            }else{
                pE.setNode(activeElements);
            }
        }
    }
}
