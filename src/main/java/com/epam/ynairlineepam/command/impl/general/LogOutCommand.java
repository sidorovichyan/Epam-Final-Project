package com.epam.ynairlineepam.command.impl.general;

import com.epam.ynairlineepam.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand implements Command {

    private static final String WELCOME_PAGE = "/?command=WelcomePage";

    private static final String USER_ID_SESSION = "idUser";
    private static final String USER_LOGIN_SESSION = "loginUser";
    private static final String USER_ROLE_SESSION = "roleUser";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute(USER_ID_SESSION);
            session.removeAttribute(USER_LOGIN_SESSION);
            session.removeAttribute(USER_ROLE_SESSION);
        }
        response.sendRedirect(WELCOME_PAGE);
    }


}
