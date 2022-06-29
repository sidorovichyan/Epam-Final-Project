package com.epam.ynairlineepam.command;

import com.epam.ynairlineepam.command.exception.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();

    private Map<String, Command> commands;

    private CommandProvider(){
        commands = new HashMap<>();
    }

    public static CommandProvider getInstance(){
        return INSTANCE;
    }

    public void addCommand(String commandName, Command command){
        commands.put(commandName, command);
    }

    public Command getCommand(String commandName) throws CommandNotFoundException {
        Command command = commands.get(commandName);
        if(command != null) {
            return command;
        } else{
            throw new CommandNotFoundException("Wrong command name");
        }
    }
}
