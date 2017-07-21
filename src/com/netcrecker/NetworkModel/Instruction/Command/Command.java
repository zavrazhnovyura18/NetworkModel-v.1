package com.netcrecker.NetworkModel.Instruction.Command;

import com.netcrecker.NetworkModel.Instruction.WorkSpace;

import java.util.List;


public interface Command {
    void execute();
    void setParameters(List<String> parameters, WorkSpace workSpace);
}
