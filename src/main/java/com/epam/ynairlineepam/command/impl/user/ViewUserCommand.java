package com.epam.ynairlineepam.command.impl.user;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.UserService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewUserCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewUserListCommand.class);

    private static final String USERS_REQUEST_ATTR = "user_details";

    private static final String USER_REQUEST_ID = "clientId";

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/workspace.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        int id = CommandHelper.getInt(request.getParameter(USER_REQUEST_ID));
        try {
            UserDetails user =  userService.findUserDetailsById(id);
            request.setAttribute(USERS_REQUEST_ATTR,user);
        } catch (ServiceException e) {
            logger.warn(e);
            e.printStackTrace();
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
