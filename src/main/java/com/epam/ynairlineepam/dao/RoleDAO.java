package com.epam.ynairlineepam.dao;

import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getListRole() throws DAOException;

    List<Role> getListPositions() throws DAOException;

    public int getRoleByString(String str) throws DAOException;
}
