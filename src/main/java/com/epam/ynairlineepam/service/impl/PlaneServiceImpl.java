package com.epam.ynairlineepam.service.impl;

import com.epam.ynairlineepam.dao.PlaneDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.factory.DAOFactory;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.PlaneService;
import com.epam.ynairlineepam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlaneServiceImpl implements PlaneService {

    private final static Logger logger = Logger.getLogger(PlaneServiceImpl.class);

    @Override
    public void addNewPlane(String departureAirport, String departureDateTime, String arrivalAirport,
                            String arrivalDateTime, String planeType, int numberPilot, int numberPersonal, int numberNavigator,
                            int numberRadioman) throws ServiceException {
       // Валидация

        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        try {
            planeDAO.insertPlane(departureAirport,departureDateTime,arrivalAirport,
                    arrivalDateTime,planeType,numberPilot,numberPersonal,numberNavigator,numberRadioman);
        } catch (DAOException e) {
            // logger
            throw new ServiceException("Can not insertPlane at Service. "+ e);
        }

    }

    @Override
    public List<Plane> getListDepartingPlane() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        List<Plane> planeList = null;
        try {
            planeList = planeDAO.getListDepartingPlane();
            Collections.sort(planeList, new Comparator<Plane>() {
                final SimpleDateFormat formatterPlane = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
                @Override
                public int compare(Plane plane1, Plane plane2) {
                    Calendar datePlane1 = Calendar.getInstance();
                    Calendar datePlane2 = Calendar.getInstance();
                    try {
                        datePlane1.setTime(formatterPlane.parse(plane1.getDepartureDateTime()));
                        datePlane2.setTime(formatterPlane.parse(plane2.getDepartureDateTime()));
                        return datePlane1.compareTo(datePlane2);
                    } catch (ParseException e) {
                        return -1;
                    }
                }
            });

        } catch (DAOException e) {
            throw new ServiceException("Can not get plane list Service."+e);
        }
        return planeList;
    }

    @Override
    public void deleteEmployeeFromPlane(int idUser, int idPlane) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        try {
            planeDAO.deleteEmployeeFromPlane(idUser,idPlane);
        } catch (DAOException e) {
            throw new ServiceException("Can not delete plane Service."+e);
        }
    }

    @Override
    public List<Plane> getListDepartingPlaneUser(int idUser) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        List<Plane> planeList = null;
        try {
            planeList = planeDAO.getListDepartingPlaneUser(idUser);
            Collections.sort(planeList, new Comparator<Plane>() {
                final SimpleDateFormat formatterPlane = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
                @Override
                public int compare(Plane plane1, Plane plane2) {
                    Calendar datePlane1 = Calendar.getInstance();
                    Calendar datePlane2 = Calendar.getInstance();
                    try {
                        datePlane1.setTime(formatterPlane.parse(plane1.getDepartureDateTime()));
                        datePlane2.setTime(formatterPlane.parse(plane2.getDepartureDateTime()));
                        return datePlane1.compareTo(datePlane2);
                    } catch (ParseException e) {
                        return -1;
                    }
                }
            });

        } catch (DAOException e) {
            throw new ServiceException("Can not get plane list Service."+e);
        }
        return planeList;
    }

    @Override
    public List<Plane> getAllPlaneList() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        List<Plane> planeList;
        try {
            planeList = planeDAO.getAllPlaneList();
            Collections.reverse(planeList);
        } catch (DAOException e) {
            // logger
            throw new ServiceException("Can not get plane list Service."+e);
        }
        return planeList;
    }

    @Override
    public HashMap<UserDetails,String> getFreeEmployee(int idPlane, String position) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        HashMap<UserDetails,String> userDetailsList = null;
        try {
            Plane plane = planeDAO.getPlaneById(idPlane);
            userDetailsList = planeDAO.getUsersFree(idPlane,position);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return userDetailsList;
    }

    @Override
    public Plane getPlaneById(int id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        Plane plane = null;
        try {
            plane = planeDAO.getPlaneById(id);
        } catch (DAOException e) {
            //logger
            throw new ServiceException(e);
        }
        return plane;
    }

    @Override
    public List<UserDetails> getUsersOnPlane(int idPlane) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        List<UserDetails> userDetailsList = null;
        try {
            userDetailsList = planeDAO.getUsersOnPlane(idPlane);
        } catch (DAOException e) {
            //logger
            throw new ServiceException(e);
        }
        return userDetailsList;
    }

    @Override
    public void addEmployeeToPlane(int employeeId, int planeId) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        PlaneDAO planeDAO = daoFactory.getMySQLPlaneDAO();
        try {
            planeDAO.addEmployeeToPlane(employeeId,planeId);
        } catch (DAOException e) {
            // logger
            throw new ServiceException(e);
        }
    }
}
