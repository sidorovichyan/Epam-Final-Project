package com.epam.ynairlineepam.command.impl.error;

import com.epam.ynairlineepam.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Error implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/error404.jsp";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
