package com.netcrecker.NetworkModel;

import com.netcrecker.NetworkModel.Exception.InvalidSyntaxCommand;
import com.netcrecker.NetworkModel.Instruction.Command.*;
import com.netcrecker.NetworkModel.Instruction.Facade;
import com.netcrecker.NetworkModel.Instruction.Invoker;
import com.netcrecker.NetworkModel.RouteProvider.RouteProvider;
import com.netcrecker.NetworkModel.RouteProvider.RouteProviderImpl;
import com.netcrecker.NetworkModel.Serialization.NetworkParser;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EntryPoint {
    public static void main(String[] args) throws Exception{
        new EntryPoint().workApp();

    }

    public void workApp(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Facade facade = new Facade();

        while (true) {
            System.out.print("Введите команду: ");
            try {
                String str = reader.readLine();
                facade.doAction(str);

            } catch (IOException ex) {
                System.out.println("Некоректный ввод");
            }
        }
    }
}

