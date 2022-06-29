package com.epam.ynairlineepam.service.impl;


import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.dao.UserDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.factory.DAOFactory;
import com.epam.ynairlineepam.entity.PilotDetails;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.UserService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getListUser() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getMysqlUserDAO();
        List<User> userList = null;
        try {
            userList = userDAO.getListUser();
        } catch (DAOException e) {

            throw new ServiceException("Error when trying to get list users"+UserServiceImpl.class);
        }
        return userList;
    }

    @Override
    public UserDetails findUserDetailsById(int id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getMysqlUserDAO();
        UserDetails user = null;
        try {
            user = userDAO.findUserDetailsById(id);
        } catch (DAOException e) {
           throw new ServiceException("Can not find user " + e);
        }
        return user;
    }

    @Override
    public void addNewPilot(String login,String password,String fio, String gender, String position,int age,String phone,String address,int flyingHouse,String qualification) throws ServiceException {
       // валидация
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getMysqlUserDAO();
        try {
            userDAO.insertPilot(login,password,fio,gender,position,age,phone,address,flyingHouse,qualification);
        } catch (DAOException e) {
            logger.error("Can not add new Pilot " + e);
            throw new ServiceException("Can not add new Pilot " + e);
        }
    }

    @Override
    public void addNewUser(String login, String password, String role, String fullName, String gender, int age, String phone, String address) throws ServiceException {
        // валидация
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getMysqlUserDAO();

        try {
            userDAO.insertUser(login, password, role, fullName, gender, age, phone, address);
        } catch (DAOException e) {
            logger.error("Can not add new Employee " + e);
            throw new ServiceException("Can not add new Employee " + e);
        }
    }

}
