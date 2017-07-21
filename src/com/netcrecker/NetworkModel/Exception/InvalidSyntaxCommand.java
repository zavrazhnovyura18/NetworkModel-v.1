package com.netcrecker.NetworkModel.Exception;

/**
 * Исключение, генерируемое при исключительной ситуации,
 * которая возникает при использовании неверного формата ввода данных.
 */
public class InvalidSyntaxCommand extends Exception {
    /**
     * Конструктор исключения,
     * использующий конструктор класса Exception.
     */
    public InvalidSyntaxCommand() {
        super("Неверный формат ввода команды.");
    }
}
