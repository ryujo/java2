package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class UserDAOImplTest extends CrudJdbcDAOTest<User, UserDAOImpl> {

    @Before
    @Override
    public void before() {
        cleaner.cleanDatabase();
        super.before();
    }


    @Override
    protected void fillRecordWithData(User user) {
        user.setEmail("a@b" + random.nextInt(100000) + ".com");
        user.setFullName("Name Surname " + random.nextInt(100000));
        user.setPassword("pass" + random.nextInt(100000));
        user.setAdmin(false);
    }

    @Override
    protected void makeChangesInRecord(User user) {
        user.setEmail("a." + user.getEmail());
        user.setFullName("Dr " + user.getFullName());
        user.setPassword("meow" + user.getPassword());
    }

    @Test(expected = DBException.class)
    public void createSecondUserWithSameEmailTest() {
        User user = new User();
        fillRecordWithData(user);
        user.setEmail(recordFromDAO.getEmail());
        dao.create(user);
    }

    @Test(expected = DBException.class)
    public void updateEmailToMatchAnotherEmailTest() {
        User user = new User();
        fillRecordWithData(user);
        user.setEmail("unique@ynuque.com");
        dao.create(user);
        recordFromDAO.setEmail(user.getEmail());
        dao.update(recordFromDAO);
    }

    @Test
    public void getByEmailWorksWithightId() {
        User user = dao.getByEmail(recordFromDAO.getEmail());
        compareRecords(recordFromDAO, user);
    }

    @Test
    public void findWithWrongEmailReturnsNull() {
        assertNull(dao.getByEmail("bad.bad@mail.com"));
    }

    @Test(expected = DBException.class)
    public void updatingWithNullFieldsFails() {
        recordFromDAO.setFullName(null);
        dao.update(recordFromDAO);
    }

}