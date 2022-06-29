package com.epam.ynairlineepam.dao;


import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;


import java.util.List;

public interface UserDAO {

    void insertUser (String login, String password, String position, String fullName, String gender, int age, String phone, String address) throws DAOException;

    void insertPilot(String login, String password, String position, String fullName, String gender, int age, String phone, String address, int flyingHours, String qualification) throws DAOException;

    void update (User user) throws DAOException;

    void delete (User user) throws DAOException;

    UserDetails findUserDetailsById(int id) throws DAOException;

    User findByLogin(String login) throws DAOException;

    List<User> getListUser() throws DAOException;

}
