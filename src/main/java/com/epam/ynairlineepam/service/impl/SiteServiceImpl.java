package com.epam.ynairlineepam.service.impl;

import com.epam.ynairlineepam.dao.UserDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.factory.DAOFactory;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.service.SiteService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.exception.ServiceLoginException;
import com.epam.ynairlineepam.service.exception.ServicePasswordException;
import org.apache.log4j.Logger;

public class SiteServiceImpl implements SiteService {

    private final static Logger logger = Logger.getLogger(SiteServiceImpl.class);

    @Override
    public User singIn(String login, String password) throws ServiceException, ServiceLoginException, ServicePasswordException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getMysqlUserDAO();
        User user = null;
        try {
            user = userDAO.findByLogin(login);
            if(user == null)
            {
                throw new ServiceLoginException("Wrong login");
            }
            if(!user.getPassword().equals(password))
            {
                throw new ServicePasswordException("Wrong password");
            }
        } catch (DAOException e) {
            throw new ServiceException(this.getClass().getName()+". Error while find used. "+e);
        }
        return user;
    }

    @Override
    public boolean registration(User user) {
        return false;
    }
}
