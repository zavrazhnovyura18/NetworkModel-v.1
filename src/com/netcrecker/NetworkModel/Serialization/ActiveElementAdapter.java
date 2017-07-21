package com.netcrecker.NetworkModel.Serialization;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class ActiveElementAdapter extends XmlAdapter<ActiveElementAdapter.LinkActiveElement,ActiveElement>{

    @XmlRootElement
    public static class LinkActiveElement{
        @XmlAttribute
        String link;

        @XmlAttribute
        String nameNetwork;

        public LinkActiveElement(String link,String nameNetwork) {
            this.link = link;
            this.nameNetwork = nameNetwork;
        }

        public LinkActiveElement() {
        }
    }

    @Override
    public ActiveElement unmarshal(LinkActiveElement v) throws Exception {

        NetworkParser.setLoadactiveElements(v.link);

        return null;

    }

    @Override
    public LinkActiveElement marshal(ActiveElement v) throws Exception {
        return new LinkActiveElement(v.getId(),v.getNetworkName());
    }
}
