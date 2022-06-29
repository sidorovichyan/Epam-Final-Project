package com.epam.ynairlineepam.command.impl.plane;

import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.impl.user.ViewUserListCommand;
import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.dao.PlaneDAO;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.UserService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ViewPlaneCommand implements Command {

    private final static Logger logger = Logger.getLogger(ViewPlaneCommand.class);

    private static final String PLANE_REQUEST_ATTR = "planeInfo";

    private static final String PLANE_REQUEST_ID = "planeId";

    private static final String PLANE_REQUEST_TEAM = "teamPlaneList";

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/workspace.jsp";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        PlaneService planeService = serviceFactory.getPlaneService();
        int id = CommandHelper.getInt(request.getParameter(PLANE_REQUEST_ID));
        try {
            Plane plane = planeService.getPlaneById(id);
            List<UserDetails> userDetailsList = planeService.getUsersOnPlane(id);
            HashMap<String,Integer> positions = plane.getNumberPosition();
            for (UserDetails user : userDetailsList)
            {
                int numb = positions.get(user.getPosition());
                numb = numb -1;
                positions.put(user.getPosition(),numb);
            }

            request.setAttribute(PLANE_REQUEST_ATTR,plane);
            request.setAttribute(PLANE_REQUEST_TEAM,userDetailsList);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);


    }
}
