package com.epam.ynairlineepam.dao.impl;



import com.epam.ynairlineepam.dao.UserDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.impl.connectionpool.ConnectionPool;
import com.epam.ynairlineepam.dao.impl.connectionpool.exeption.ConnectionPoolException;
import com.epam.ynairlineepam.dao.impl.rolehelper.RoleHelper;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO implements UserDAO {

    private static final String INSERT_USER_QUERY = "INSERT INTO `users` " +
            "(`login`, `password`, `roles_id`) " +
            "VALUES (?, ?, ?)";

    private static final String SELECT_USER_DETAILS_BY_ID_QUERY = "SELECT u.full_name,u.gender,u.age,u.phone, u.address, p.position, k.login " +
            "FROM `users-details` u " +
            "INNER JOIN positions p on u.positions_id = p.id " +
            "INNER JOIN users k on u.users_id_user = k.id_user " +
            "WHERE u.users_id_user = ? ";

    private static final String SELECT_USER_BY_LOGIN_QUERY = "SELECT  u.id_user, u.login, u.password, r.title " +
            "FROM users U " +
            "INNER JOIN roles R on u.roles_id =r.id " +
            "WHERE login = ? ";

    private static final String SELECT_ALL_USER_QUERY = "SELECT  u.id_user, u.login, u.password, r.title " +
            "FROM users U " +
            "INNER JOIN roles R on u.roles_id =r.id";

    private static final String INSERT_PILOT_QUERY = "INSERT INTO `pilot-info` (flying_hours,qualification,users_id_user)" +
            "VALUES (?,?,?) ";

    private static final String INSERT_USER_DETAILS_QUERY = "INSERT INTO `users-details` (full_name,gender,age,phone,address,users_id_user,positions_id)" +
            "VALUES (?,?,?,?,?,?,?)";



    private ConnectionPool connectionPool;

    @Override
    public void insertUser(String login, String password, String position, String fullName, String gender, int age, String phone, String address) throws DAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            RoleHelper roleHelper = RoleHelper.getInstance();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            int roleID = roleHelper.getIdRoleByPositionString(position);
            preparedStatement.setInt(3,roleID);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            String resID = null;
            if (rs.next()){
                resID=rs.getString(1);
            }
            int lastID = Integer.parseInt(resID);

            PreparedStatement preparedStatement1 = connection.prepareStatement(INSERT_USER_DETAILS_QUERY);
            preparedStatement1.setString(1,fullName);
            preparedStatement1.setString(2,gender);
            preparedStatement1.setInt(3,age);
            preparedStatement1.setString(4,phone);
            preparedStatement1.setString(5,address);
            preparedStatement1.setInt(6,lastID);
            roleID = roleHelper.getIdPositionByString(position);
            preparedStatement1.setInt(7,roleID);
            preparedStatement1.executeUpdate();


            connection.commit();

            preparedStatement1.close();
            connectionPool.closeConnection(connection,preparedStatement);



        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(MySQLUserDAO.class+ " DAOException. Impossible insert User " + e);
        }


    }

    @Override
    public void insertPilot(String login,String password,String fio, String gender, String position,int age,String phone,String address,int flyingHouse,String qualification) throws DAOException {
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = null;
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            RoleHelper roleHelper = RoleHelper.getInstance();

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            int roleID = roleHelper.getIdRoleByPositionString(position);
            preparedStatement.setInt(3,roleID);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            String resID = null;
            if (rs.next()){
                resID=rs.getString(1);
            }
            int lastID = Integer.parseInt(resID);

            PreparedStatement preparedStatement1 = connection.prepareStatement(INSERT_USER_DETAILS_QUERY);
            preparedStatement1.setString(1,fio);
            preparedStatement1.setString(2,gender);
            preparedStatement1.setInt(3,age);
            preparedStatement1.setString(4,phone);
            preparedStatement1.setString(5,address);
            preparedStatement1.setInt(6,lastID);
            roleID = roleHelper.getIdPositionByString(position);
            preparedStatement1.setInt(7,roleID);
            preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(INSERT_PILOT_QUERY);
            preparedStatement2.setInt(1,flyingHouse);
            preparedStatement2.setString(2,qualification);
            preparedStatement2.setInt(3,lastID);
            preparedStatement2.executeUpdate();

            try {
                connection.commit();
            }catch (SQLException e)
            {
                connection.rollback();
                throw new DAOException("Can not commit transaction. "+ e);
            }

            preparedStatement1.close();
            preparedStatement2.close();
            connectionPool.closeConnection(connection,preparedStatement);

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Error. "+ e );
        }


    }

    @Override
    public void update(User user) throws DAOException {

    }

    @Override
    public void delete(User user) throws DAOException {

    }

    @Override
    public UserDetails findUserDetailsById(int id) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        UserDetails user = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_DETAILS_BY_ID_QUERY);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                user = new UserDetails();
                user.setFullName(resultSet.getString(1));
                user.setGender(resultSet.getString(2));
                user.setAge(resultSet.getInt(3));
                user.setPhone(resultSet.getString(4));
                user.setAddress(resultSet.getString(5));
                user.setPosition(resultSet.getString(6));
                user.setLogin(resultSet.getString(7));
            }
            connectionPool.closeConnection(connection,preparedStatement);

        } catch (ConnectionPoolException |SQLException e) {
            throw new DAOException("Can not find user details"+e);
        }
        return user;
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        User user = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_QUERY);
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
            }
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getListUser() throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        User user = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER_QUERY);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRole(resultSet.getString(4));
                userList.add(user);
            }
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
           //logger
            e.printStackTrace();
        }
        return userList;
    }
}
