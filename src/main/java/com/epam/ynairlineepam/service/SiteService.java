package com.epam.ynairlineepam.service;

import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.service.exception.ServiceException;
import com.epam.ynairlineepam.service.exception.ServiceLoginException;
import com.epam.ynairlineepam.service.exception.ServicePasswordException;

public interface SiteService {

    User singIn(String login, String password) throws ServiceException, ServiceLoginException, ServicePasswordException;

    boolean registration (User user);

}
