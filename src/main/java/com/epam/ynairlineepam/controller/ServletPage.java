package com.epam.ynairlineepam.controller;



import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.CommandProvider;
import com.epam.ynairlineepam.command.exception.CommandNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;


public class ServletPage extends HttpServlet {

    private final CommandProvider provider = CommandProvider.getInstance();
    private static final String COMMAND = "command";

    private static final int PAGE_NOT_FOUND_ERROR = 404;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String commandName = request.getParameter(COMMAND);
            Command command = provider.getCommand(commandName);
            command.execute(request, response);
        } catch (CommandNotFoundException e){
            response.sendError(PAGE_NOT_FOUND_ERROR);
        }
    }

}
