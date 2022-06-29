package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.impl.user.ViewUserListCommand;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ViewAllPlaneListCommand implements Command {

    private final static Logger logger = Logger.getLogger(ViewUserListCommand.class);

    private static final String PLANE_LIST_REQUEST = "allPlanes";

    private static final String WORK_PAGE = "/?command=workspace";

    private static final String MAIN_PAGE = "/?command=WelcomePage";

    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        try {
            List<Plane> planeList = planeService.getAllPlaneList();
            request.setAttribute(PLANE_LIST_REQUEST,planeList);
        } catch (ServiceException e) {
            logger.warn(e);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
            return;
        }
        request.getRequestDispatcher(WORK_PAGE).forward(request, response);
    }
}
