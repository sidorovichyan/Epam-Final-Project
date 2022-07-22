package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.dao.impl.rolehelper.RoleHelper;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class ViewEmployeeCanAddPlaneCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewEmployeeCanAddPlaneCommand.class);

    private static final String PLANE_REQUEST_ID = "planeId";

    private static final String POSITION_EMPLOYEE = "position";

    private static final String PLANE_REQUEST_CAN_TEAM = "canAddToPlaneList";

    private static final String WORK_PAGE = "/?command=viewPlane";

    private static final String ERROR_REQUEST_ATTR = "error";
    private static final String SUCCESS_REQUEST_ATTR = "success";

    private static final String AMP = "&";

    private static final String EQ = "=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();

        int id = CommandHelper.getInt(request.getParameter(PLANE_REQUEST_ID));
        String position = request.getParameter(POSITION_EMPLOYEE);
        RoleHelper roleHelper = RoleHelper.getInstance();
        int idPos = roleHelper.getIdPositionByString(position);
        if(idPos != 0)
        {
            try {
                HashMap<UserDetails,String> userDetailsList = planeService.getFreeEmployee(id,position);
                request.setAttribute(PLANE_REQUEST_CAN_TEAM,userDetailsList);
                request.getRequestDispatcher(WORK_PAGE+AMP+PLANE_REQUEST_ID+EQ+id+AMP+POSITION_EMPLOYEE+EQ+position).forward(request, response);
            } catch (ServiceException e) {
                logger.warn(e);
                response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
            }
        }else
        {
            logger.warn("Not found position: "+ position);
            response.sendRedirect(WORK_PAGE+AMP+ERROR_REQUEST_ATTR+EQ+true);
        }


    }
}
