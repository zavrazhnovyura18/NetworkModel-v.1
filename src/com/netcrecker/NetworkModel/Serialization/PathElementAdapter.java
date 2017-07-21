package com.netcrecker.NetworkModel.Serialization;

import com.netcrecker.NetworkModel.Network.PathElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class PathElementAdapter extends XmlAdapter<PathElementAdapter.LinkNode,PathElement> {

    @XmlRootElement
    public static class LinkNode{
        @XmlAttribute
        String link;

        @XmlAttribute
        String nameNetwork;

        public LinkNode(String link,String nameNetwork) {
            this.link = link;
            this.nameNetwork = nameNetwork;
        }

        public LinkNode() {
        }
    }

    @Override
    public PathElement unmarshal(LinkNode v) throws Exception {
        StringBuilder path = new StringBuilder(v.nameNetwork);
        path.append("/").append(v.link);
        return new NetworkParser().getOtherObject(path.toString());
    }

    @Override
    public LinkNode marshal(PathElement v) throws Exception {
        return new LinkNode(v.getId(),v.getNetworkName());
    }
}
