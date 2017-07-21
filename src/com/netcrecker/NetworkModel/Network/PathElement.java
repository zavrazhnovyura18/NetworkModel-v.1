package com.netcrecker.NetworkModel.Network;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Serialization.PathElementAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;


@XmlAccessorType(XmlAccessType.NONE)
@XmlJavaTypeAdapter(PathElementAdapter.class)
public interface PathElement {

    String getId();

    String getNetworkName();

    String getInfo();

    List<ActiveElement> getConnections();

}
