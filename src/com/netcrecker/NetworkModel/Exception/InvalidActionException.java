package com.netcrecker.NetworkModel.Exception;

/**
 * Исключение, генерируемое при исключительной ситуации,
 * которая возникает при попытке совершить абсурдные вещи с сетью,
 * к примеру найти маршрут из узла в узел.
 */
public class InvalidActionException extends Exception{
    /**
     * Конструктор исключения,
     * использующий конструктор класса Exception.
     * @param cause причина возникновения ошибки
     */
    public InvalidActionException(String cause) {
        super(cause);
    }
}
