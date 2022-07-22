package com.epam.ynairlineepam.dao.impl;

import com.epam.ynairlineepam.command.impl.plane.AddNewPlaneCommand;
import com.epam.ynairlineepam.dao.PlaneDAO;
import com.epam.ynairlineepam.dao.exception.DAOException;
import com.epam.ynairlineepam.dao.impl.connectionpool.ConnectionPool;
import com.epam.ynairlineepam.dao.impl.connectionpool.exeption.ConnectionPoolException;
import com.epam.ynairlineepam.dao.impl.rolehelper.RoleHelper;
import com.epam.ynairlineepam.entity.PilotDetails;
import com.epam.ynairlineepam.entity.Plane;
import com.epam.ynairlineepam.entity.User;
import com.epam.ynairlineepam.entity.UserDetails;
import com.epam.ynairlineepam.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class MySQLPlaneDAO implements PlaneDAO {

    private final static Logger logger = Logger.getLogger(MySQLPlaneDAO.class);

    private final static String SELECT_ID_USER_BY_ID_USER_DETAILS = "SELECT u.id_user FROM ynairline.`users-details` f " +
            "INNER JOIN users u on u.id_user = f.users_id_user " +
            "WHERE id = ?";

    private static final String INSERT_USERS_HAS_FLIGHT = "INSERT INTO `users_has_flight` " +
            "(users_id_user,flight_id) " +
            "VALUES(?,?)";


    private static final String INSERT_PLANE_QUERY = "INSERT INTO flight " +
            "(departure_airport,departure_date,arrival_airport,arrival_date,plane)" +
            "VALUES(?,?,?,?,?)";

    private static final String SELECT_PLANE_DEPARTURE = "SELECT * FROM flight where departure_date > now()";

    private static final String SELECT_PLANE_POSITION = "SELECT p.count, r.position FROM flight_has_positions p " +
            "inner join positions r on p.positions_id = r.id " +
            "WHERE flight_id = ?";

    private static final String SELECT_PLANE_BY_ID = "SELECT * FROM flight WHERE id = ?";

    private static final String SELECT_USER_DETAILS_ON_PLANE = "SELECT p.id, p.full_name, p.gender, p.age, p.phone, p.address, r.position from users_has_flight u " +
            "inner join `users-details` p on u.users_id_user = p.users_id_user " +
            "inner join positions r on p.positions_id = r.id " +
            "where flight_id = ?";

    private static final String INSERT_NUMBER_POSITIONS = "INSERT INTO `flight_has_positions`" +
            "(count,flight_id,positions_id)" +
            "VALUES (?,?,?)";

    private static final String SELECT_NUMBER_POSITIONS_PLANE = "SELECT * FROM `flight_has_positions` " +
            "WHERE `flight_id` = ?";

    private static final String SELECT_NUMBER_USERS_ON_PLANE = "SELECT * FROM `users_has_flight` " +
            "WHERE `flight_id` = ?";

    private static final String SELECT_ALL_PLANES = "SELECT * FROM flight";

    private static final String SELECT_USERS_FREE = "SELECT p.id, p.full_name, p.gender, p.age, p.phone, p.address, r.position, f.arrival_airport, f.arrival_date from users_has_flight u " +
            "inner join `users-details` p on u.users_id_user = p.users_id_user " +
            "inner join positions r on p.positions_id = r.id " +
            "inner join flight f on u.flight_id = f.id " +
            "where flight_id != ? AND  r.position = ?";

    private static final String NEVER_DO_FLY = "SELECT p.id, p.full_name, p.gender, p.age, p.phone, p.address, r.position from `users-details` p " +
            "inner join positions r on p.positions_id = r.id " +
            "inner join users_has_flight fl on fl.users_id_user != p.id " +
            "where r.position = ? ";

    private static final String SELECT_USER_PLANE = "SELECT f.id,f.departure_airport,f.departure_date,f.arrival_airport,f.arrival_date,f.plane FROM ynairline.users_has_flight us " +
            "INNER JOIN flight f on us.flight_id = f.id " +
            "WHERE (us.users_id_user = ? and f.departure_date>now())";

    private static final String DELETE_EMPLOYEE_FROM_FLIGHT = "DELETE FROM ynairline.users_has_flight us where exists " +
            "(select * from flight f, `users-details` usd " +
            "where f.id = ?  " +
            "and usd.id = ? " +
            "and usd.users_id_user = us.users_id_user " +
            "and f.departure_date > now() " +
            "and us.flight_id = ?) ";

    private static final String POSITION_PILOT = "Пилот";
    private static final String POSITION_NAVIGATOR = "Штурман";
    private static final String POSITION_RADIOMAN = "Радист";
    private static final String POSITION_PERSONAL = "Стюардесса";


    private ConnectionPool connectionPool;
    private RoleHelper roleHelper;

    @Override
    public void insertPlane(String departureAirport, String departureDateTime, String arrivalAirport, String arrivalDateTime, String planeType, int numberPilot, int numberPersonal, int numberNavigator, int numberRadioman) throws DAOException {
        Connection connection;
        try {
            roleHelper = RoleHelper.getInstance();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PLANE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, departureAirport);
            preparedStatement.setString(2, departureDateTime);
            preparedStatement.setString(3, arrivalAirport);
            preparedStatement.setString(4, arrivalDateTime);
            preparedStatement.setString(5, planeType);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            String resID = null;
            if (rs.next()) {
                resID = rs.getString(1);
            }
            int lastIDPlane = Integer.parseInt(resID);

            PreparedStatement preparedStatement1 = connection.prepareStatement(INSERT_NUMBER_POSITIONS);
            preparedStatement1.setInt(1, numberPilot);
            preparedStatement1.setInt(2, lastIDPlane);
            preparedStatement1.setInt(3, roleHelper.getIdPositionByString(POSITION_PILOT));
            preparedStatement1.addBatch();

            preparedStatement1.setInt(1, numberNavigator);
            preparedStatement1.setInt(2, lastIDPlane);
            preparedStatement1.setInt(3, roleHelper.getIdPositionByString(POSITION_NAVIGATOR));
            preparedStatement1.addBatch();

            preparedStatement1.setInt(1, numberRadioman);
            preparedStatement1.setInt(2, lastIDPlane);
            preparedStatement1.setInt(3, roleHelper.getIdPositionByString(POSITION_RADIOMAN));
            preparedStatement1.addBatch();

            preparedStatement1.setInt(1, numberPersonal);
            preparedStatement1.setInt(2, lastIDPlane);
            preparedStatement1.setInt(3, roleHelper.getIdPositionByString(POSITION_PERSONAL));
            preparedStatement1.addBatch();

            preparedStatement1.executeBatch();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.warn("Can not commit transaction. " + e);
                throw new DAOException("Can not commit transaction. " + e);
            }
            preparedStatement1.close();
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            logger.warn("Can not add new plane DAO. " + e);
            throw new DAOException("Can not add new plane DAO. " + e);
        }
    }

    @Override
    public void addEmployeeToPlane(int employeeId, int planeId) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        ResultSet resultSet;
        Connection connection;
        int idUser = -1;
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_USER_BY_ID_USER_DETAILS);
            preparedStatement.setInt(1,employeeId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                idUser = resultSet.getInt(1);
            }
            preparedStatement = connection.prepareStatement(INSERT_USERS_HAS_FLIGHT);
            preparedStatement.setInt(1,idUser);
            preparedStatement.setInt(2,planeId);
            preparedStatement.executeUpdate();
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            logger.warn("Can not add new employee to plane DAO. " + e);
            throw new DAOException("Can not add new employee to plane DAO. " + e);
        }
    }

    @Override
    public List<Plane> getAllPlaneList() throws DAOException {
        ResultSet resultSet = null;
        List<Plane> planeList = new ArrayList<>();
        Plane plane;
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PLANES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plane = new Plane();
                plane.setId(resultSet.getInt(1));
                plane.setDepartureAirport(resultSet.getString(2));

                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(3));
                plane.setDepartureDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));

                plane.setArrivalAirport(resultSet.getString(4));
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(5));
                plane.setArrivalDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));
                plane.setPlaneType(resultSet.getString(6));
                chekingTeamPlane(plane, connection);
                planeList.add(plane);
            }
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException | ParseException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database " + e);
        }
        return planeList;
    }

    @Override
    public List<Plane> getListDepartingPlane() throws DAOException {
        ResultSet resultSet = null;
        List<Plane> planeList = new ArrayList<>();
        Plane plane;
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PLANE_DEPARTURE);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plane = new Plane();
                plane.setId(resultSet.getInt(1));
                plane.setDepartureAirport(resultSet.getString(2));

                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(3));
                plane.setDepartureDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));

                plane.setArrivalAirport(resultSet.getString(4));
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(5));
                plane.setArrivalDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));
                plane.setPlaneType(resultSet.getString(6));
                chekingTeamPlane(plane, connection);
                planeList.add(plane);
            }
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException | ParseException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database " + e);
        }
        return planeList;
    }

    @Override
    public void deleteEmployeeFromPlane(int idUser, int idPlane) throws DAOException {
        Connection connection;
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_FROM_FLIGHT);
            preparedStatement.setInt(1,idPlane);
            preparedStatement.setInt(2,idUser);
            preparedStatement.setInt(3,idPlane);
            preparedStatement.executeUpdate();
            connectionPool.closeConnection(connection,preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            logger.warn("Can not delete records from Database "  + e);
            throw new DAOException("Can not delete records from Database " + e);
        }
    }

    @Override
    public List<Plane> getListDepartingPlaneUser(int idUser) throws DAOException {
        ResultSet resultSet = null;
        List<Plane> planeList = new ArrayList<>();
        Plane plane;
        try {
            connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_PLANE);
            preparedStatement.setInt(1,idUser);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plane = new Plane();
                plane.setId(resultSet.getInt(1));
                plane.setDepartureAirport(resultSet.getString(2));

                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(3));
                plane.setDepartureDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));

                plane.setArrivalAirport(resultSet.getString(4));
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(5));
                plane.setArrivalDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));
                plane.setPlaneType(resultSet.getString(6));
                chekingTeamPlane(plane, connection);
                planeList.add(plane);
            }
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException | ParseException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database " + e);
        }
        return planeList;
    }

    @Override
    public Plane getPlaneById(int id) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        ResultSet resultSet = null;
        Plane plane = null;
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PLANE_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                plane = new Plane();
                plane.setId(resultSet.getInt(1));
                plane.setDepartureAirport(resultSet.getString(2));

                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(3));
                plane.setDepartureDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));

                plane.setArrivalAirport(resultSet.getString(4));
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(5));
                plane.setArrivalDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));
                plane.setPlaneType(resultSet.getString(6));
            }
            preparedStatement = connection.prepareStatement(SELECT_PLANE_POSITION);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            HashMap<String, Integer> hashMapPositions = new HashMap<>();
            while (resultSet.next()) {
                hashMapPositions.put(resultSet.getString(2), resultSet.getInt(1));
            }
            plane.setNumberPosition(hashMapPositions);
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException | ParseException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database " + e);
        }
        return plane;

    }

    @Override
    public HashMap<UserDetails, String> getUsersFree(int idPlane, String position) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        ResultSet resultSet;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat formatterPlane = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
        HashMap<UserDetails, String> hashMap = new HashMap<>();
        UserDetails user;
        Plane plane = this.getPlaneById(idPlane);

        Date date;

        Calendar dateUser = Calendar.getInstance();

        Calendar datePlane = Calendar.getInstance();

        try {
            datePlane.setTime(formatterPlane.parse(plane.getDepartureDateTime()));
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_FREE);
            preparedStatement.setInt(1, idPlane);
            preparedStatement.setString(2, position);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (plane.getDepartureAirport().equals(resultSet.getString(8))) {
                    dateUser.setTime(formatter.parse(resultSet.getString(9)));
                    if (dateUser.get(Calendar.YEAR) == datePlane.get(Calendar.YEAR) &&
                            dateUser.get(Calendar.MONTH) <= datePlane.get(Calendar.MONTH)) {
                        if(dateUser.get(Calendar.MONTH) == datePlane.get(Calendar.MONTH))
                        {
                            if(dateUser.get(Calendar.DAY_OF_MONTH) >= datePlane.get(Calendar.DAY_OF_MONTH))
                            {
                                break;
                            }
                        }
                        user = new UserDetails();
                        user.setId(resultSet.getInt(1));
                        user.setFullName(resultSet.getString(2));
                        user.setGender(resultSet.getString(3));
                        user.setAge(resultSet.getInt(4));
                        user.setPhone(resultSet.getString(5));
                        user.setAddress(resultSet.getString(6));
                        user.setPosition(resultSet.getString(7));
                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString(9));
                        hashMap.put(user,new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date) + " - " + resultSet.getString(8));
                    }
                }
            }
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(NEVER_DO_FLY);
            preparedStatement.setString(1, position);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new UserDetails();
                user.setId(resultSet.getInt(1));
                user.setFullName(resultSet.getString(2));
                user.setGender(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                user.setPhone(resultSet.getString(5));
                user.setAddress(resultSet.getString(6));
                user.setPosition(resultSet.getString(7));
                hashMap.putIfAbsent(user, "Нет рейсов");

            }
            connectionPool.closeConnection(connection, preparedStatement);


        } catch (ConnectionPoolException | SQLException | ParseException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database "  + e);
        }
        return hashMap;


    }

    @Override
    public List<UserDetails> getUsersOnPlane(int idPlane) throws DAOException {
        connectionPool = ConnectionPool.getInstance();
        ResultSet resultSet = null;
        UserDetails user = null;
        List<UserDetails> userDetailsList = new ArrayList<>();
        try {
            Connection connection = connectionPool.takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_DETAILS_ON_PLANE);
            preparedStatement.setInt(1, idPlane);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new UserDetails();
                user.setId(resultSet.getInt(1));
                user.setFullName(resultSet.getString(2));
                user.setGender(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                user.setPhone(resultSet.getString(5));
                user.setAddress(resultSet.getString(6));
                user.setPosition(resultSet.getString(7));
                userDetailsList.add(user);
            }
            connectionPool.closeConnection(connection, preparedStatement);
        } catch (ConnectionPoolException | SQLException e) {
            logger.warn("Can not get records from Database "  + e);
            throw new DAOException("Can not get records from Database " + e);
        }
        return userDetailsList;
    }

    private void chekingTeamPlane(Plane plane, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NUMBER_POSITIONS_PLANE);
        preparedStatement.setInt(1, plane.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        int numbNeed = 0;
        int numbHave = 0;
        while (resultSet.next()) {
            numbNeed = numbNeed + resultSet.getInt(2);
        }
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(SELECT_NUMBER_USERS_ON_PLANE);
        preparedStatement.setInt(1, plane.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            numbHave++;
        }
        plane.setTeamFull(numbNeed == numbHave);
        preparedStatement.close();
    }

}
