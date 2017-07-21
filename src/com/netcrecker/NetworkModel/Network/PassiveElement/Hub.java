package com.netcrecker.NetworkModel.Network.PassiveElement;

import com.netcrecker.NetworkModel.Exception.InvalidConnectionException;
import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.PathElement;
import com.netcrecker.NetworkModel.Serialization.ActiveElementAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Hub extends PassiveElement {

    @XmlElement
    private int numOfConnections;
    public int getNumOfConnections() {
        return numOfConnections;
    }
    public void setNumOfConnections(int numOfConnections) {
        this.numOfConnections = numOfConnections;
    }

    @XmlElementWrapper(name = "ConnectNode")
    @XmlElement(name = "node")
    @XmlJavaTypeAdapter(ActiveElementAdapter.class)
    private List<ActiveElement> connections;
    public void setConnections(ActiveElement ... pathElements) throws InvalidConnectionException {
        for (int i = 0; i < pathElements.length; i++) {
            if((connections.size() + 1) > numOfConnections){
                throw new InvalidConnectionException("Ну это уже наглость, ты пытаешься впихнуть невпихуемое, ты не видишь что-ли портов уже нет");
            }
            this.connections.add(pathElements[i]);
        }
    }

    @Override
    public void setNode(List<ActiveElement> activeElement) {
        this.connections = activeElement;
    }

    @Override
    public List<ActiveElement> getConnections() {
        return this.connections;
    }

    public Hub(float channelCapacity, String networkName, int numOfConnections) {
        super(channelCapacity,networkName);
        connections = new ArrayList<>();
        this.numOfConnections = numOfConnections;
    }

    public Hub() {
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder("   Hub{\n");
        builder.append("    id = ").append(getId()).append("\n");
        builder.append("    channelCapacity = ").append(getChannelCapacity()).append("\n");
        builder.append("    numOfConnections = ").append(numOfConnections).append("\n");
        builder.append("    [\n");
        for (PathElement p:connections) {
            builder.append("     idNeighbour = ").append(p.getId()).append("\n");
        }
        builder.append("    ]\n}");
        return builder.toString();
    }
}
