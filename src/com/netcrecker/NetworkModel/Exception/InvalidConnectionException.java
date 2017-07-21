package com.netcrecker.NetworkModel.Exception;

/**
 * Исключение, генерируемое при исключительной ситуации,
 * которая возникает при попытке неправильного соединения узлов.
 */
public class InvalidConnectionException extends Exception {
    /**
     * Конструктор исключения,
     * использующий конструктор класса Exception.
     * @param cause причина возникновения ошибки
     */
    public InvalidConnectionException(String cause) {
        super(cause);
    }

}
