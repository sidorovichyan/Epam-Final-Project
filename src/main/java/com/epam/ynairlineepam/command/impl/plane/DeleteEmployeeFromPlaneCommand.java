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

public class DeleteEmployeeFromPlaneCommand implements Command {

    private final static Logger logger = Logger.getLogger(AddNewPlaneCommand.class);

    private static final String ID_USER = "idUser";
    private static final String ID_PLANE = "planeID";

    private static final String ID_PLANE_URL = "planeId";
    private static final String WORK_PAGE = "/?command=viewPlane";

    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        int idUser = CommandHelper.getInt(request.getParameter(ID_USER));
        int idPlane = CommandHelper.getInt(request.getParameter(ID_PLANE));
        try {
            planeService.deleteEmployeeFromPlane(idUser,idPlane);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
        }
        request.setAttribute(SUCCESS_REQUEST_ATTR,SUCCESS_REQUEST_ATTR);
        request.getRequestDispatcher(WORK_PAGE + AMP + ID_PLANE_URL + EQ + idPlane).forward(request, response);
    }
}
