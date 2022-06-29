package com.epam.ynairlineepam.service;


import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    List<User> getListUser() throws ServiceException;

    UserDetails findUserDetailsById(int id) throws ServiceException;

    void addNewPilot(String login,String password,String fio, String gender, String position,int age,String phone,String address,int flyingHouse,String qualification) throws ServiceException;

    void addNewUser(String login, String password, String role, String fullName, String gender, int age, String phone, String address) throws ServiceException;
}
