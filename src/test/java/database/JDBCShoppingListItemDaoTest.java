package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ShoppingListItem;

class JDBCShoppingListItemDaoTest {

    private JDBCShoppingListItemDao dao = new JDBCShoppingListItemDao();

    /**
     * This method clears the test database and inserts two rows directly in the
     * database before each test with a delete statement.
     * 
     * This way every time the tests are executed they have exactly the same data to
     * work with.
     * 
     * !! Make sure to always use a different database environment variable for each
     * execution environment to prevent data loss or corruption !!
     */
    @BeforeEach
    public void setUp() throws Exception {
        Connection connection = dao.connect();
        connection.prepareStatement("delete from ShoppingListItem").executeUpdate();
        connection.prepareStatement("insert into ShoppingListItem (id, title) values (1, 'Milk')").executeUpdate();
        connection.prepareStatement("insert into ShoppingListItem (id, title) values (2, 'Eggs')").executeUpdate();
        connection.close();
    }

    @AfterEach
    public void cleanUp() throws Exception {
        Connection connection = dao.connect();
        connection.prepareStatement("delete from ShoppingListItem").executeUpdate();
        connection.close();
    }

    @Test
    public void testGetAllItems() {
        List<ShoppingListItem> allItems = dao.getAllItems();

        assertEquals(2, allItems.size());
        assertEquals("Milk", allItems.get(0).getTitle());
    }

    @Test
    public void testGetItemById() {
        ShoppingListItem item1 = dao.getItem(1L);

        assertEquals(1L, item1.getId());
        assertEquals("Milk", item1.getTitle());
    }

    @Test
    public void testGetItemByUnknownId() {
        ShoppingListItem item = dao.getItem(1234L);

        assertEquals(null, item);
    }

    @Test
    public void testAddItem() {
        ShoppingListItem honey = new ShoppingListItem("Honey");

        assertTrue(dao.addItem(honey));
        assertTrue(dao.getAllItems().contains(honey));
    }

    @Test
    public void testRemoveItem() {
        ShoppingListItem milk = new ShoppingListItem(1L, "Milk");

        assertTrue(dao.removeItem(milk));
        assertFalse(dao.getAllItems().contains(milk));
    }
}
