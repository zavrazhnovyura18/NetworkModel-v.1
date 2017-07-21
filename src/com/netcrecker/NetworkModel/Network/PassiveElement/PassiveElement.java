package com.netcrecker.NetworkModel.Network.PassiveElement;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.PathElement;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlType
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({Cable.class,Hub.class})
public abstract class PassiveElement implements PathElement{

    @XmlAttribute
    private String id;
    @Override
    public String getId() {
        return this.id;
    }

    @XmlAttribute
    private String networkName;
    @Override
    public String getNetworkName() {
        return this.networkName;
    }

    @XmlElement
    private float channelCapacity;
    public float getChannelCapacity() {
        return channelCapacity;
    }
    public void setChannelCapacity(float channelCapacity) {
        this.channelCapacity = channelCapacity;
    }

    public abstract void setNode(List<ActiveElement> activeElement);

    public PassiveElement(float channelCapacity, String networkName) {
        this.id = (int) (Math.random() * Integer.MAX_VALUE) + "";
        this.channelCapacity = channelCapacity;
        this.networkName = networkName;
    }

    public PassiveElement() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassiveElement)) return false;

        PassiveElement that = (PassiveElement) o;

        if (Float.compare(that.channelCapacity, channelCapacity) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return networkName != null ? networkName.equals(that.networkName) : that.networkName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (networkName != null ? networkName.hashCode() : 0);
        result = 31 * result + (channelCapacity != +0.0f ? Float.floatToIntBits(channelCapacity) : 0);
        return result;
    }
}
