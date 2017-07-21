package com.netcrecker.NetworkModel.Exception;

/**
 * Исключение, генерируемое при исключительной ситуации,
 * которая возникает при отсутсвии маршрута между точками.
 */
public class RouteNotFoundException extends Exception {
    /**
     * Конструктор исключения,
     * использующий конструктор класса Exception.
     */
    public RouteNotFoundException() {
        super("Данные точки не соединены между собой");
    }
}
