package com.epam.ynairlineepam.dao;

import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.entity.UserDetails;


import java.util.HashMap;
import java.util.List;

public interface PlaneDAO {

    void insertPlane(String departureAirport, String departureDateTime, String arrivalAirport,
                     String arrivalDateTime, String planeType, int numberPilot,
                     int numberPersonal, int numberNavigator,
                     int numberRadioman) throws DAOException;

    void addEmployeeToPlane(int employeeId, int planeId) throws DAOException;

    List<Plane> getAllPlaneList() throws DAOException;

    List<Plane> getListDepartingPlane() throws DAOException;

    void deleteEmployeeFromPlane(int idUser, int idPlane) throws DAOException;

    List<Plane> getListDepartingPlaneUser(int idUser) throws DAOException;

    Plane getPlaneById(int id) throws  DAOException;

    HashMap<UserDetails,String> getUsersFree(int idPlane, String position) throws DAOException;

    List<UserDetails> getUsersOnPlane(int idPlane) throws DAOException;


}
