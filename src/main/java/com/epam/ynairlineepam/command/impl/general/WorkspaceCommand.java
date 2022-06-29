package com.epam.ynairlineepam.command.impl.general;

import com.epam.ynairlineepam.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WorkspaceCommand implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/workspace.jsp";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String USER_LOGIN_SESSION = "loginUser";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request,response);
    }

}
