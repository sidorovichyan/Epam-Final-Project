package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddEmployeeToPlaneCommand implements Command {


    private final static Logger logger = Logger.getLogger(AddEmployeeToPlaneCommand.class);

    private static final String WORK_PAGE = "/?command=workspace";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String PLANE_ID = "planeId";

    private static final String EMPLOYEE_ID = "employeeId";


    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = CommandHelper.getInt(request.getParameter(EMPLOYEE_ID));
        int planeId = CommandHelper.getInt(request.getParameter(PLANE_ID));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        try {
            planeService.addEmployeeToPlane(employeeId,planeId);
            response.sendRedirect(WORK_PAGE+AMP+SUCCESS_REQUEST_ATTR+EQ+true);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
        }

    }
}
