package com.epam.ynairlineepam.command.impl.general;

import com.epam.ynairlineepam.command.Command;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class WelcomeCommand implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/welcome.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request,response);
    }
}
