package com.netcrecker.NetworkModel.Network.PassiveElement;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Serialization.ActiveElementAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Cable extends PassiveElement {

    @XmlElement(name = "ConnectNode")
    @XmlJavaTypeAdapter(ActiveElementAdapter.class)
    private ActiveElement node;
    @Override
    public void setNode(List<ActiveElement> node) {
        this.node = node.get(0);
    }

    public Cable(float channelCapacity, String networkName, ActiveElement node) {
        super(channelCapacity,networkName);
        this.node = node;
    }

    public Cable() {
    }

    @Override
    public List<ActiveElement> getConnections()  {
        List<ActiveElement> elements = new ArrayList<>();
        elements.add(node);
        return elements;
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder("   Cable{\n");

        builder.append("    id = ").append(getId()).append("\n");
        builder.append("    channelCapacity = ").append(getChannelCapacity()).append("\n");
        builder.append("    node = ").append(node.getId()).append("\n");
        builder.append("   }");

        return builder.toString();
    }


}
