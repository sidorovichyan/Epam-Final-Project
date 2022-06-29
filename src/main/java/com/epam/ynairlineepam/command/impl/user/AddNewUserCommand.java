package com.epam.ynairlineepam.command.impl.user;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.dao.UserDAO;
import com.epam.ynairlineepam.entity.PilotDetails;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.UserService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddNewUserCommand implements Command {

    private final static Logger logger = Logger.getLogger(AddNewUserCommand.class);

    private static final String USER_LOGIN_SESSION = "loginUser";

    private static final String WORK_PAGE = "/?command=workspace";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FIO = "FIO";
    private static final String USER_GENDER = "gender";
    private static final String USER_POSITION = "position";
    private static final String PILOT_HOUSE = "flying-hours";
    private static final String PILOT_QUALIFICATION = "qualification";
    private static final String USER_PILOT_POSITION = "Пилот";
    private static final String USER_AGE = "age";
    private static final String USER_PHONE = "phone";
    private static final String USER_ADDRESS = "address";

    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        if(session == null)
//        {
//            response.sendRedirect(MAIN_PAGE);
//            return;
//        }
//        if(session.getAttribute(USER_LOGIN_SESSION) == null)
//        {
//            response.sendRedirect(MAIN_PAGE);
//            return;
//        }

        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(USER_PASSWORD);
        String fio = request.getParameter(USER_FIO);
        String gender = request.getParameter(USER_GENDER);
        String position = request.getParameter(USER_POSITION);
        int age = CommandHelper.getInt(request.getParameter(USER_AGE));
        String phone = request.getParameter(USER_PHONE);
        String address = request.getParameter(USER_ADDRESS);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        if (position.equals(USER_PILOT_POSITION))
        {
            int flyingHouse =CommandHelper.getInt(request.getParameter(PILOT_HOUSE));
            String qualification = request.getParameter(PILOT_QUALIFICATION);
            try {
                userService.addNewPilot(login,password,fio,gender,position,age,phone,address,flyingHouse,qualification);
                response.sendRedirect(WORK_PAGE+AMP+SUCCESS_REQUEST_ATTR+EQ+true);
            } catch (ServiceException e) {
                logger.warn(e);
                response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
            }
        }else
        {
            try {
                userService.addNewUser(login,password,position,fio,gender,age,phone,address);
                response.sendRedirect(WORK_PAGE+AMP+SUCCESS_REQUEST_ATTR+EQ+true);
            } catch (ServiceException e) {
                logger.warn(e);
                response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
            }
        }


    }
}
