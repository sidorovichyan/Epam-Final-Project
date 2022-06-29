package com.epam.ynairlineepam.service.factory;


import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.SiteService;
import com.epam.ynairlineepam.service.UserService;
import com.epam.ynairlineepam.service.impl.PlaneServiceImpl;
import com.epam.ynairlineepam.service.impl.SiteServiceImpl;
import com.epam.ynairlineepam.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();

    private final SiteService siteService = new SiteServiceImpl();

    private final PlaneService planeService = new PlaneServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance()
    {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public SiteService getSiteService() {
        return siteService;
    }

    public PlaneService getPlaneService() {
        return planeService;
    }
}
