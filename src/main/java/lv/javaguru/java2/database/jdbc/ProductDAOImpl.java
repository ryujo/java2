package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by algis on 16.21.7.
 */
public class ProductDAOImpl extends DAOImpl{

    private final String GET_ALL_QUERY_PHRASE = "select * from products";
    private final String GET_BY_ID_PHRASE = "select * from products where id = ?";
    private final String GET_ATTRIBUTES_BY_ID_PHRASE = "select * from product_attributes where product_id = ?";
    private final String DELETE_ATTRIBUTES_BY_ID_PHRASE = "delete from product_attributes where product_id = ?";
    private final String DELETE_BY_ID_PHRASE = "delete from products where id = ?";

    private Product getFromResultSet(Connection connection , ResultSet resultSet) throws SQLException{
        Product product;
        product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setImagePath(resultSet.getString("imagepath"));
        product.setPrice(resultSet.getDouble("price"));
        product.setCategoryId(resultSet.getLong("category_id"));

        PreparedStatement preparedStatementForAttributes = connection.prepareStatement(GET_ATTRIBUTES_BY_ID_PHRASE);
        preparedStatementForAttributes.setLong( 1, product.getId());
        resultSet = preparedStatementForAttributes.executeQuery();
        Map<String,String> attributes = new HashMap<String, String>();
        while (resultSet.next()){
            attributes.put( resultSet.getString("attribute") , resultSet.getString("attr_value"));
        }
        product.setAttributes(attributes);

        return product;
    }


    public Product getById(Long id) throws DBException{
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_PHRASE);
            preparedStatement.setLong( 1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = getFromResultSet(connection , resultSet);
            }
            return product;
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Product> getAll(QueryTuning queryTuning) throws DBException{
        return getAllBySQL(queryTuning.tuneQuery(GET_ALL_QUERY_PHRASE));
    }
    public List<Product> getAll() throws DBException{
        return getAllBySQL(GET_ALL_QUERY_PHRASE);
    }

    private List<Product> getAllBySQL(String sql) throws DBException{
        Connection connection = null;
        List<Product> products = new ArrayList<Product>();
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Product product = getFromResultSet(connection , resultSet);
                products.add(product);
            }
            return products;

        }
        catch (Throwable e) {
            System.out.println("Exception while execute SQL: " + sql);
            e.printStackTrace();
            throw new DBException(e);
        }
        finally {
            closeConnection(connection);
        }
    }

    private void createAttributes(Connection connection  , Product product) throws SQLException{
        for (Map.Entry<String, String> attribute : product.getAttributes().entrySet()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into product_attributes(product_id,attribute,attr_value) values ( ? , ?, ? )");
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, attribute.getKey());
            preparedStatement.setString(3, attribute.getValue());
            preparedStatement.executeUpdate();
        }
    }
    private void deleteAttributes(Connection connection  , Product product) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTRIBUTES_BY_ID_PHRASE);
        preparedStatement.setLong(1 , product.getId());
        preparedStatement.executeUpdate();
    }

    public void delete(Product product) throws DBException {
        if (product == null) {
            return;
        }
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_PHRASE);
            preparedStatement.setLong(1,product.getId());
            preparedStatement.executeUpdate();
            deleteAttributes(connection,product);
            product.setId(0);
        } catch (Throwable e) {
            System.out.println("Exception while execute productDAO.delete");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void update(Product product) throws DBException {
        if (product == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update products set name = ? , description = ? , price = ? , imagepath = ? , category_id = ? where id = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getImagePath());
            preparedStatement.setLong(5, product.getCategoryId());
            preparedStatement.setLong(6 , product.getId());
            preparedStatement.executeUpdate();

            deleteAttributes(connection,product);
            createAttributes(connection,product);
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public void create(Product product) throws DBException {
        if (product == null) {
            return;
        }
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into products(id,name,description,price,imagepath,category_id) values(default,?,?,?,?,?)" ,
                                                PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getImagePath());
            preparedStatement.setLong(5, product.getCategoryId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                product.setId(rs.getLong(1));
            }
            createAttributes(connection,product);
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAO.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }


}