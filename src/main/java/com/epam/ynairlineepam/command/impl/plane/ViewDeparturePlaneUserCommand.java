package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewDeparturePlaneUserCommand implements Command {

    private final static Logger logger = Logger.getLogger(ViewDeparturePlaneUserCommand.class);

    private static final String PLANE_LIST_REQUEST = "allDeparturePlanes";

    private static final String WORK_PAGE = "/?command=workspace";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String EMPTY_LIST_PLANE = "emptyListPlane";
    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String USER_REQUEST_ID = "idUser";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        int idUser = CommandHelper.getInt(request.getParameter(USER_REQUEST_ID));
        try {
            List<Plane> planeList = planeService.getListDepartingPlaneUser(idUser);
            if(planeList == null || planeList.isEmpty())
            {
                request.setAttribute(EMPTY_LIST_PLANE,EMPTY_LIST_PLANE);
            }
            request.setAttribute(PLANE_LIST_REQUEST,planeList);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
            return;
        }
        request.getRequestDispatcher(WORK_PAGE).forward(request, response);
    }
}
