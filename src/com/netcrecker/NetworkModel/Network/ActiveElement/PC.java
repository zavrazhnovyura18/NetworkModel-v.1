package com.netcrecker.NetworkModel.Network.ActiveElement;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class PC extends ActiveElement {

    public PC(String IP, double costs, String nameNetwork) {
        super(IP, 2, costs,nameNetwork);
    }

    public PC() {
    }
}
