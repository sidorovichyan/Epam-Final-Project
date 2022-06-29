package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.impl.user.AddNewUserCommand;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddNewPlaneCommand implements Command {


    private final static Logger logger = Logger.getLogger(AddNewPlaneCommand.class);

    private static final String WORK_PAGE = "/?command=workspace";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String USER_LOGIN_SESSION = "loginUser";

    private static final String PLANE_DEPARTURE_AIRPORT_REQUEST = "departure_airport";
    private static final String PLANE_DEPARTURE_DATE_REQUEST = "departure_date";
    private static final String PLANE_ARRIVAL_AIRPORT_REQUEST = "arrival_airport";
    private static final String PLANE_ARRIVAL_DATE_REQUEST = "arrival_date";
    private static final String PLANE_MODEL_REQUEST = "type-plane";
    private static final String PLANE_NUMBER_PILOT_REQUEST = "n-pilots";
    private static final String PLANE_NUMBER_NAVIGATOR_REQUEST = "n-navigator";
    private static final String PLANE_NUMBER_PERSONAL_REQUEST = "n-personal";
    private static final String PLANE_NUMBER_RADIOMAN_REQUEST = "n-radioman";

    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departureAirport = request.getParameter(PLANE_DEPARTURE_AIRPORT_REQUEST);
        String departureDateTime = request.getParameter(PLANE_DEPARTURE_DATE_REQUEST);
        String arrivalAirport = request.getParameter(PLANE_ARRIVAL_AIRPORT_REQUEST);;
        String arrivalDateTime = request.getParameter(PLANE_ARRIVAL_DATE_REQUEST);;
        String planeType = request.getParameter(PLANE_MODEL_REQUEST);
        int numberPilot = CommandHelper.getInt(request.getParameter(PLANE_NUMBER_PILOT_REQUEST));
        int numberPersonal = CommandHelper.getInt(request.getParameter(PLANE_NUMBER_PERSONAL_REQUEST));
        int numberNavigator = CommandHelper.getInt(request.getParameter(PLANE_NUMBER_NAVIGATOR_REQUEST));
        int numberRadioman = CommandHelper.getInt(request.getParameter(PLANE_NUMBER_RADIOMAN_REQUEST));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        try {
            planeService.addNewPlane(departureAirport, departureDateTime, arrivalAirport,
                    arrivalDateTime, planeType, numberPilot,
                    numberPersonal, numberNavigator, numberRadioman);
            response.sendRedirect(WORK_PAGE+AMP+SUCCESS_REQUEST_ATTR+EQ+true);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
