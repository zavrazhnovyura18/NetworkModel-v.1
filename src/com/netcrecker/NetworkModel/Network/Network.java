package com.netcrecker.NetworkModel.Network;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Serialization.NetworkParser;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Network {

    @XmlAttribute
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "Nodes")
    @XmlElement(name = "node")
    private List<PathElement> nodes;
    public List<PathElement> getNodes() {
        return nodes;
    }
    public void setNodes(PathElement ... node) {
        for (int i = 0; i < node.length; i++) {
            nodes.add(node[i]);
        }
        this.linksPathElemetns = castPathToLinks(this.nodes);
    }

    @XmlTransient
    private static String pathToProviders = "src/ru/donkey/NetworkModel/RouteProvider/";

    @XmlTransient
    private int[][] linksPathElemetns;
    public int[][] getLinksPathElemetns() {
        return castPathToLinks(nodes);
    }

    public List<Class> getProviders(){
        File dir = new File(pathToProviders);
        String[] files = dir.list();
        String path = pathToProviders.replaceAll("/",".").substring(4);

        for (int i = 0; i < files.length; i++) {

            int length = files[i].length();
            files[i] = path + files[i].substring(0,length - 5);
        }

        return checkInterface(files, "ru.donkey.NetworkModel.RouteProvider.RouteProvider");
    }
    private List<Class> checkInterface(String[] files, String nameInterface){
        List<Class> classes = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            try {
                Class tempClass = Class.forName(files[i]);

                Class[] interfaces = tempClass.getInterfaces();
                for (int j = 0; j < interfaces.length; j++) {

                    if(interfaces[j].getName().equals(nameInterface)){
                        classes.add(tempClass);
                        break;
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }
    private int[][] castPathToLinks(List<PathElement> pathElementList){

        ArrayList<ArrayList<Integer>> links = new ArrayList<>();

        for (PathElement pE : pathElementList) {

            int idParent = Integer.parseInt(pE.getId());
            List<ActiveElement> tempElement = pE.getConnections();

            for (ActiveElement aE : tempElement) {

                int idDaughter = Integer.parseInt(aE.getId());

                ArrayList<Integer> rows = new ArrayList<>();
                rows.add(idParent);
                rows.add(idDaughter);

                links.add(rows);
            }
        }

        int[][] masLinks = new int[links.size()][2];
        for (int i = 0; i < links.size(); i++) {
            ArrayList<Integer> temp = links.get(i);
            for (int j = 0; j < temp.size(); j++) {
                masLinks[i][j] = temp.get(j);
            }
        }

        return masLinks;
    }

    public Network(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
        this.linksPathElemetns = new int[0][0];
    }

    public Network(String name, boolean isNew) throws JAXBException{
        this(new NetworkParser().getRootObject(name));
    }

    private Network(Network network) {
        this.name = network.getName();
        this.nodes = network.getNodes();
    }

    public Network() {
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Network network = (Network) o;

        if (name != null ? !name.equals(network.name) : network.name != null) return false;
        return nodes != null ? nodes.equals(network.nodes) : network.nodes == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
        return result;
    }
}
