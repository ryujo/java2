package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.domain.Category;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("JDBC_CategoryDAO")
public class CategoryDAOImpl extends DAOImpl<Category> implements CategoryDAO {

    private final String CREATE_NEW = "INSERT INTO categories(name,father_id,id) VALUES(?,0,DEFAULT)";
    private final String UPDATE_BY_ID = "UPDATE categories SET name = ? WHERE id = ?";
    private final String DELETE_BY_ID = "DELETE FROM categories WHERE id = ?";
    private final String GET_BY_ID = "SELECT * FROM categories where id = ?";
    private final String GET_ALL = "SELECT * FROM categories";

    @Override
    public long create(Category category) {
        return super.create(category, CREATE_NEW);
    }

    @Override
    public void update(Category category) {
        super.update(category, UPDATE_BY_ID);
    }

    @Override
    public void delete(Category category) {
        super.delete(category, DELETE_BY_ID);
    }

    @Override
    public Category getById(long id) {
        return (Category) getById(id, GET_BY_ID);
    }

    @Override
    public List<Category> getAll() {
        return super.getAll(GET_ALL);
    }

    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getName());
        if (category.getId() != 0)
            preparedStatement.setLong(2, category.getId());
    }

    @Override
    protected Category buildFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setName(resultSet.getString("name"));
        category.setId(resultSet.getLong("id"));
        return category;
    }
}
