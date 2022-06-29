package com.epam.ynairlineepam.service;

import com.epam.ynairlineepam.command.util.CommandHelper;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface PlaneService {

    void addNewPlane(String departureAirport, String departureDateTime, String arrivalAirport,
                     String arrivalDateTime, String planeType, int numberPilot,
                     int numberPersonal, int numberNavigator,
                     int numberRadioman) throws ServiceException;

    List<Plane> getListDepartingPlane() throws ServiceException;

    List<Plane> getAllPlaneList() throws ServiceException;

    HashMap<UserDetails,String> getFreeEmployee(int idPlane,String position) throws ServiceException;

    Plane getPlaneById (int id) throws ServiceException;

    List<UserDetails> getUsersOnPlane (int idPlane) throws ServiceException;

    void addEmployeeToPlane(int employeeId, int planeId) throws ServiceException;

}
