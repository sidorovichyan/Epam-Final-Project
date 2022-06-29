package com.epam.ynairlineepam.command.impl.general;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.impl.plane.AddNewPlaneCommand;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.service.SiteService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.exception.ServiceLoginException;
import com.epam.ynairlineepam.service.exception.ServicePasswordException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInCommand implements Command {

    private final static Logger logger = Logger.getLogger(LogInCommand.class);


    private static final String REDIRECT_PAGE = "/?command=workspace";

    private static final String WELCOME_PAGE = "/?command=WelcomePage";

    private static final String LOGIN_FORM = "loginForm";
    private static final String PASSWORD_FORM = "loginPass";

    private static final String USER_ID_SESSION = "idUser";
    private static final String USER_LOGIN_SESSION = "loginUser";
    private static final String USER_ROLE_SESSION = "roleUser";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_LOGIN_REQUEST_ATTR = "wrongLogin";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";

    private static final String AMP = "&";

    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN_FORM);
        String password = request.getParameter(PASSWORD_FORM);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SiteService siteService =serviceFactory.getSiteService();
        User user = null;
        if(login != null)
        {
            try {
                user = siteService.singIn(login,password);
                HttpSession session =request.getSession(true);
                session.setAttribute(USER_ID_SESSION,user.getId());
                session.setAttribute(USER_LOGIN_SESSION,user.getLogin());
                session.setAttribute(USER_ROLE_SESSION,user.getRole());
                response.sendRedirect(REDIRECT_PAGE);
            } catch (ServiceException e) {
                logger.warn(e);
                response.sendRedirect(WELCOME_PAGE+AMP+SERVICE_ERROR_REQUEST_ATTR+EQ+true);
            } catch (ServicePasswordException e) {
                logger.warn(e);
                response.sendRedirect(WELCOME_PAGE+AMP+WRONG_PASSWORD_REQUEST_ATTR+EQ+true);
            } catch (ServiceLoginException e) {
                logger.warn(e);
                response.sendRedirect(WELCOME_PAGE+AMP+WRONG_LOGIN_REQUEST_ATTR+EQ+true);
            }
        }else
        {
            request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
        }
    }
}
