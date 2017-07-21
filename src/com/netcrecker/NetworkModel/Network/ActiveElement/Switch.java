package com.netcrecker.NetworkModel.Network.ActiveElement;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Switch extends ActiveElement {

    public Switch(String IP, int numOfConnections, double costs, String nameNetwork) {
        super(IP, numOfConnections, costs,nameNetwork);
    }

    public Switch() {
    }
}
