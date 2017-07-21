package com.netcrecker.NetworkModel.RouteProvider;

import com.netcrecker.NetworkModel.Exception.InvalidActionException;
import com.netcrecker.NetworkModel.Exception.RouteNotFoundException;
import com.netcrecker.NetworkModel.Network.ActiveElement.ActiveElement;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RouteProviderImpl implements RouteProvider{

    private List<String> list = Arrays.asList("MinCostProvider","MinCountNodeProvider","MinTimeDelayProvider");

    @Override
    public List<PathElement> getRoute(Integer firstID, Integer secondId, Network net, String provider) throws RouteNotFoundException, InvalidActionException {

        if(firstID.equals(secondId)){
            throw new InvalidActionException("Странная вещь, вы пытаетесь найти путь из узла в узел, буквально себя же поймать за хвост");
        }

        ArrayList<Integer> result = new ArrayList<>(); //Сюда, будут складываться все найденные маршруты
        getRoutes(net.getLinksPathElemetns(),firstID,secondId,result, new ArrayList<>());

        if(result.size() == 0){
            throw new RouteNotFoundException();
        }

        ArrayList<ArrayList<Integer>> sortRes = shatterRoutes(result,secondId);

        List<Integer> resultMapping = null;
        switch (provider){
            case "MinCostProvider": resultMapping = sumCost(sortRes,net.getNodes());
                break;
            case "MinCountNodeProvider": resultMapping = countNode(sortRes);
                break;
            case "MinTimeDelayProvider": resultMapping = sumTimeDelay(sortRes,net.getNodes());
                break;
        }

        int index = minProperty(resultMapping);

        return bindLinks(sortRes,index,net.getNodes());
    }


    /**
     * Метод, подсчитывает стоимость маршрутов
     * @param sortRes маршруты
     * @param pathElements элементы сети
     * @return массив, содержащий стоимости маршрутов
     */
    private List<Integer> sumCost(ArrayList<ArrayList<Integer>> sortRes, List<PathElement> pathElements){
        List<Integer> sumCosts = new ArrayList<>();

        for (int i = 0; i < sortRes.size(); i++) {
            ArrayList<Integer> temp = sortRes.get(i);
            int cost = 0;

            for (int j = 0; j < temp.size(); j++) {
                int idNode = temp.get(j);

                for (PathElement pE : pathElements) {
                    int id = Integer.parseInt(pE.getId());
                    if(id == idNode) {
                        ActiveElement activeElement = (ActiveElement) pE;
                        cost += activeElement.getCosts();
                    }
                }
            }

            sumCosts.add(cost);
            cost = 0;
        }

        return sumCosts;
    }

    /**
     * Метод, возвращает список, который содержит количество узлов в маршруте, для каждого найденного маршрута
     * @param sortRes список маршрутов
     * @return список количества узлов
     */
    private List<Integer> countNode(ArrayList<ArrayList<Integer>> sortRes){
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < sortRes.size(); i++) {
            integers.add(sortRes.get(i).size());
        }
        return integers;
    }

    /**
     * Метод, возвращает список, содержащий количество затраченного времени при прохождении маршрута, для каждого маршрута
     * @param sortRes список маршрутов
     * @param pathElements элементы сети
     * @return список, затраченного времени на каждый маршрут
     */
    private List<Integer> sumTimeDelay(ArrayList<ArrayList<Integer>> sortRes, List<PathElement> pathElements){
        List<Integer> sumTimeDelay = new ArrayList<>();

        for (int i = 0; i < sortRes.size(); i++) {
            ArrayList<Integer> temp = sortRes.get(i);
            int timeDelay = 0;

            for (int j = 0; j < temp.size(); j++) {
                int idNode = temp.get(j);

                for (PathElement pE : pathElements) {
                    int id = Integer.parseInt(pE.getId());
                    if(id == idNode) {
                        ActiveElement activeElement = (ActiveElement) pE;
                        timeDelay += activeElement.getTimeDelay();
                    }
                }
            }

            sumTimeDelay.add(timeDelay);
            timeDelay = 0;
        }

        return sumTimeDelay;
    }

    /**
     * Метод, получает на вход значения маршрутов по выбранным свойствам провайдера
     * @param list массив, содержащий значения для каждого маршрута по выбранным свойствам провайдера
     * @return индекс пути, на котором наименьшие затраты
     */
    private Integer minProperty(List<Integer> list){
        int minCost = list.get(0);
        int indexCost = 0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) < minCost){
                minCost = list.get(i);
                indexCost = i;
            }
        }
        return  indexCost;
    }

    @Override
    public List<String> getAlgorithm() {
        return this.list;
    }
}
