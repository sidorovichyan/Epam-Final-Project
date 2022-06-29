package com.epam.ynairlineepam.dao.impl;

import com.epam.ynairlineepam.dao.RoleDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.impl.connectionpool.ConnectionPool;
import com.epam.ynairlineepam.dao.impl.connectionpool.exeption.ConnectionPoolException;
import com.epam.ynairlineepam.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLRoleDAO implements RoleDAO {


    private ConnectionPool connectionPool;

    private static final String SELECT_ALL_ROLES = "SELECT * FROM roles";

    private static final String SELECT_ALL_POSITIONS = "SELECT * FROM positions";

    @Override
    public List<Role> getListRole() throws DAOException {
        List<Role> roleList = new ArrayList<>();
        ResultSet resultSet;
        Role role;
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.takeConnection();
            connectionPool = ConnectionPool.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                role = new Role();
                role.setId(resultSet.getInt(1));
                role.setRole(resultSet.getString(2));
                roleList.add(role);
            }
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }

    @Override
    public List<Role> getListPositions() throws DAOException {
        List<Role> roleList = new ArrayList<>();
        ResultSet resultSet;
        Role role;
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.takeConnection();
            connectionPool = ConnectionPool.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POSITIONS);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                role = new Role();
                role.setId(resultSet.getInt(1));
                role.setRole(resultSet.getString(2));
                roleList.add(role);
            }
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }


    @Override
    public int getRoleByString(String str) throws DAOException{
        return 0;
    }
}
