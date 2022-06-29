package com.epam.ynairlineepam.dao.impl.rolehelper;

import com.epam.ynairlineepam.dao.RoleDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.factory.DAOFactory;
import com.epam.ynairlineepam.entity.Role;

import java.util.HashMap;
import java.util.List;

public class RoleHelper {

    private static RoleHelper instance;

    private static HashMap<String,Integer> hashRole = new HashMap<>();

    private static HashMap<String,Integer> hashPositions = new HashMap<>();

    private static final String USER_ROLE = "user";
    private static final String ADMIN_ROLE = "admin";
    private static final String DISPATCHER_ROLE = "dispatcher";

    private static final String ADMIN_POSITION = "Администратор";
    private static final String DISPATCHER_POSITION = "Диспетчер";

    public static RoleHelper getInstance() {
        if(instance == null)
        {
            instance = new RoleHelper();
            instance.init();
        }
        return instance;
    }

    private RoleHelper(){
    }

    private void init()
    {
        DAOFactory daoFactory = DAOFactory.getInstance();
        RoleDAO roleDAO= daoFactory.getMySQLRoleDAO();
        try {
            List<Role> listRole = roleDAO.getListRole();
            for(Role role:listRole)
            {
                hashRole.put(role.getRole(),role.getId());
            }
            List<Role> listPosition = roleDAO.getListPositions();
            for(Role role: listPosition)
            {
                hashPositions.put(role.getRole(), role.getId());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

    public int getIdRoleByString (String role)
    {
        return hashRole.get(role);
    }

    public int getIdPositionByString(String position)
    {
        return hashPositions.get(position);
    }

    public int getIdRoleByPositionString(String position)
    {
        if(position.equals(ADMIN_POSITION))
        {
            return getIdRoleByString(ADMIN_ROLE);
        }else if(position.equals(DISPATCHER_POSITION))
        {
            return getIdRoleByString(DISPATCHER_ROLE);
        }
        return getIdRoleByString(USER_ROLE);
    }

}
