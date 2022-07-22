package com.epam.ynairlineepam.command.impl.general;

import com.epam.ynairlineepam.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class WorkspaceCommand implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/workspace.jsp";


    private static final String REDIRECT_PAGE_USER = "/?command=userTimetable";
    private static final String REDIRECT_PAGE_ADMIN ="/?command=viewUserList";


    private static final String ID_USER = "idUser";
    private static final String AMP = "&";

    private static final String EQ = "=";

    private static final String USER_ROLE_SESSION = "roleUser";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String POSITION_USER = "user";
    private static final String POSITION_ADMIN = "admin";
    private static final String POSITION_DISPATCHER = "disp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       if(Objects.equals(request.getParameter(USER_ROLE_SESSION), POSITION_USER))
       {
           response.sendRedirect(REDIRECT_PAGE_USER+AMP+ID_USER+EQ+request.getParameter(ID_USER));
       }else if(Objects.equals(request.getParameter(USER_ROLE_SESSION), POSITION_ADMIN))
       {
           request.getRequestDispatcher(REDIRECT_PAGE_ADMIN).forward(request, response);
           //response.sendRedirect(REDIRECT_PAGE_ADMIN);
       }else{
           request.getRequestDispatcher(JSP_PAGE_PATH).forward(request,response);
       }


    }

}
