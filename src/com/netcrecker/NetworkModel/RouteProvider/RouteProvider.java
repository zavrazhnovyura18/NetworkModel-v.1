package com.netcrecker.NetworkModel.RouteProvider;

import com.netcrecker.NetworkModel.Exception.InvalidActionException;
import com.netcrecker.NetworkModel.Exception.RouteNotFoundException;
import com.netcrecker.NetworkModel.Network.Network;
import com.netcrecker.NetworkModel.Network.PathElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс, предоставляющий функционал по нахождению пути
 * между двумя узлами
 */
public interface RouteProvider {
    /**
     * Метод, по умолчанию, который будет вовращать все возможные пути из одной точки
     * в другую.
     * @param linksPathElements сюда, будет приходить табдица связей между узлами
     * @param firstId узел, с которого начинается путь
     * @param secondId узел, на котором заканчивается путь
     * @param result список, содержащий результирующие пути
     * @param used список, содержащий уже пройденные вершины
     */
    default void getRoutes(int[][] linksPathElements, int firstId, int secondId, ArrayList<Integer> result, ArrayList<Integer> used){
        boolean isImpasse = true;

        for (int i = 0; i < linksPathElements.length; i++) {

            int startNode = linksPathElements[i][0];
            int endNode = linksPathElements[i][1];

            if (startNode == firstId) {
                if (!used.contains(endNode)) {

                    isImpasse = false;

                    result.add(startNode);
                    used.add(startNode);

                    if (endNode == secondId) {
                        result.add(endNode);
                    } else {
                        getRoutes(linksPathElements, endNode, secondId, result, used);
                    }
                }
            }
        }
        if(isImpasse){
            for (int i = result.size() - 1; i > result.lastIndexOf(secondId); i--) {
                result.remove(i);
            }
        }
    }
    /**
     * Метод, который разбивает массив полученных результатов на маршруты
     * @param routes массив, полученных путей
     * @param secondId результирующий узел, в который ищется путь
     * @return Массив маршрутов
     */
    default ArrayList<ArrayList<Integer>> shatterRoutes(ArrayList<Integer> routes, int secondId){

        ArrayList<ArrayList<Integer>> sortRes = new ArrayList<>(); //Здесь будет храниться каждый маршрут отдельно
        ArrayList<Integer> tempRoute = new ArrayList<>(); //Сюда будет записываться маршрут, до попадания в массив выше

        for (int i = 0; i < routes.size(); i++) {

            if(routes.get(i) == secondId){ //Если узел, соотвествует тому узлу, что мы искали, то
                tempRoute.add(secondId);
                sortRes.add(tempRoute); //записываем маршрут в список маршрутов
                tempRoute = new ArrayList<>(); //создаем новый список, для хранения маршрута
                continue; //Продолжаем итерацию
            }
            tempRoute.add(routes.get(i));
        }

        return sortRes;
    }
    /**
     * Метод, производит связывание ссылок на узлы с объектами узлов
     * @param sortRes список маршрутов
     * @param index необходимый маршрут
     * @param pathElem элементы сети
     * @return элементы сети, соотвествующие маршруту
     */
    default List<PathElement> bindLinks(ArrayList<ArrayList<Integer>> sortRes, int index, List<PathElement> pathElem){
        List<PathElement> pathElements = new ArrayList<>();
        for (int i = 0; i < sortRes.get(index).size(); i++) {
            int idNode = sortRes.get(index).get(i);

            for (PathElement pE : pathElem) {
                if(idNode == Integer.parseInt(pE.getId())){
                    pathElements.add(pE);
                }
            }
        }

        return pathElements;
    }
    /**
     * Определение контракта интерфейса.
     * Данный метод возвращает маршрут между двумя узлами сети.
     * @param firstID id первого узла сети.
     * @param secondId id второго узла сети.
     * @param net сеть, в которой происходит поиск
     * @return список, содержащий последовательность элементов
     * @throws RouteNotFoundException ошибка, которая говорит, что путь не найден
     * @throws RouteNotFoundException ошибка, которая говорит, что пользователь пытается
     * выполнить некорректные вещи
     */
    List<PathElement> getRoute(Integer firstID, Integer secondId, Network net, String provider) throws RouteNotFoundException, InvalidActionException;
    /**
     * Метод, возвращает предоставляемые алгоритмы провайдера
     * @return
     */
    List<String> getAlgorithm();

}
