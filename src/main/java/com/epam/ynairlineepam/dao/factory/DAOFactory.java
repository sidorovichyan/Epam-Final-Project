package com.epam.ynairlineepam.dao.factory;


import com.epam.ynairlineepam.dao.PlaneDAO;
import com.epam.ynairlineepam.dao.RoleDAO;
import com.epam.ynairlineepam.dao.UserDAO;
import com.epam.ynairlineepam.dao.impl.MySQLPlaneDAO;
import com.epam.ynairlineepam.dao.impl.MySQLRoleDAO;
import com.epam.ynairlineepam.dao.impl.MySQLUserDAO;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO mysqlUserImpl = new MySQLUserDAO();

    private final RoleDAO mySQLRoleImpl = new MySQLRoleDAO();

    private final PlaneDAO mySQLPlaneImpl = new MySQLPlaneDAO();


    private DAOFactory(){}

    public static DAOFactory getInstance()
    {
        return instance;
    }

    public UserDAO getMysqlUserDAO() {
        return mysqlUserImpl;
    }

    public RoleDAO getMySQLRoleDAO() {
        return mySQLRoleImpl;
    }

    public PlaneDAO getMySQLPlaneDAO() {
        return mySQLPlaneImpl;
    }
}
