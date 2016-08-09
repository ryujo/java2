package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends DAOImpl implements DAO<User> {

    private final String CREATE_USER_RETURN_ID = "INSERT INTO user (user_fullName, user_email, user_password) values(?,?,?)";
    private final String UPDATE_USER = "UPDATE user SET user_fullName=?, user_email=?,user_password=? WHERE user_id=?";
    private final String DELETE_USER = "DELETE FROM user WHERE user_id=?";
    private final String GET_USER_BY_ID = "SELECT * FROM user WHERE user_id=?";
    private final String GET_ALL_USERS = "SELECT * FROM user";

    public long createReturnId(User user) throws DBException {
        if(user == null)
            return 0;
        Connection connection = null;
        long newId = 0;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    (CREATE_USER_RETURN_ID, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                newId = resultSet.getLong(1);
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.createUserWithId()");
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return newId;
    }

    public void createWithId(User user) throws DBException {
        if(user == null)
            return;
        long newId = createReturnId(user);
        user.setId(newId);
    }

    public void update(User user) throws DBException {
        if(user == null)return;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.updateUser");
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void delete(User user) throws DBException {
        if(user == null)return;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            user.setId(0);
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.deleteUser()");
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public User getById(long id) throws DBException {
        if(id == 0)return null;
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    (GET_USER_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setFullName(resultSet.getString("user_fullName"));
                user.setEmail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
            }
            return user;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getUserById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<User> getAll() throws DBException {
        List<User> userList = new ArrayList<User>();

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    (GET_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setFullName(resultSet.getString("user_fullName"));
                user.setEmail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                userList.add(user);
            }
            return userList;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getAllUsers()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }
}