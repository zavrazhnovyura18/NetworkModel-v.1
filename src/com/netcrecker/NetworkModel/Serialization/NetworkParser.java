package com.netcrecker.NetworkModel.Serialization;

import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PassiveElement.PassiveElement;
import com.netcrecker.NetworkModel.Network.PathElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class NetworkParser {

    private static List<String> loadactiveElements;
    public static List<String> getLoadactiveElements() {
        return loadactiveElements;
    }
    public static void setLoadactiveElements(String ... loadactiveElements) {
        for (int i = 0; i < loadactiveElements.length; i++) {
            NetworkParser.loadactiveElements.add(loadactiveElements[i]);
        }
    }

    private static String path = "assets/NetworkModel/";

    public Network getRootObject(String fileName) throws JAXBException{

        loadactiveElements = new ArrayList<>();

        JAXBContext jaxbContext = JAXBContext.newInstance(Network.class, ActiveElement.class, PassiveElement.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Network loadNetwork = (Network) unmarshaller.unmarshal(new File(path + fileName + "/" + fileName + ".xml"));

        List<PathElement> pathElementList = loadNetwork.getNodes();
        for (int i = 0; i < pathElementList.size(); i++) {
            ActiveElement activeElement = (ActiveElement) pathElementList.get(i);

            List<PassiveElement> passiveElementList = activeElement.getPassiveElement();
            for (int j = 0; j < passiveElementList.size(); j++) {

                PassiveElement passiveElement = passiveElementList.get(j);

                List<ActiveElement> tempList = new ArrayList<>();
                for (int k = 0; k < passiveElement.getConnections().size(); k++) {
                    tempList.add(findElementById(pathElementList,loadactiveElements.get(0)));
                    loadactiveElements.remove(0);
                }
                passiveElement.setNode(tempList);

            }
        }

        return loadNetwork;
    }

    public PathElement getOtherObject(String fileName) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(Network.class, ActiveElement.class, PassiveElement.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (PathElement) unmarshaller.unmarshal(new File(path + fileName + ".xml"));
    }

    public Object getOtherObject(String fileName, Class className) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller.unmarshal(new File(fileName + ".xml"));
    }


    private ActiveElement findElementById(List<PathElement> pathElements, String id){
        for (int i = 0; i < pathElements.size(); i++) {
            PathElement pathElement = pathElements.get(i);

            if(id.equals(pathElement.getId())){
                return (ActiveElement) pathElement;
            }
        }
        return null;
    }

    public void saveObject(Network network) throws JAXBException{

        FileWorker fileWorker = new FileWorker();

        boolean isAvaliable = fileWorker.checkAvailability(network.getName(),path);
        if(isAvaliable){
            fileWorker.deleteDir(network.getName(),path);
            fileWorker.createDir(network.getName(),path);
        }else{
            fileWorker.createDir(network.getName(),path);
        }


        JAXBContext jaxbContext = JAXBContext.newInstance(Network.class, ActiveElement.class, PassiveElement.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        StringBuilder pathToRootElement = new StringBuilder(path);
        pathToRootElement.append(network.getName()).append("/").append(network.getName()).append(".xml");

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(network,new File(pathToRootElement.toString()));

        List<PathElement> pathElementList = network.getNodes();
        for (int i = 0; i < pathElementList.size(); i++) {
            StringBuilder pathToOtherElement = new StringBuilder(path);
            pathToOtherElement.append(network.getName()).append("/").append(pathElementList.get(i).getId()).append(".xml");

            marshaller.marshal(pathElementList.get(i),new File(pathToOtherElement.toString()));
        }
    }
}
