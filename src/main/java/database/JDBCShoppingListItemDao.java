package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ShoppingListItem;

public class JDBCShoppingListItemDao implements ShoppingListItemDao {

    private static final String JDBC_URL = System.getenv("JDBC_DATABASE_URL");

    @Override
    public List<ShoppingListItem> getAllItems() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        List<ShoppingListItem> allItems = new ArrayList<>();

        try {
            connection = connect();
            statement = connection.prepareStatement("select * from ShoppingListItem order by id asc");
            result = statement.executeQuery();

            while (result.next()) {
                long id = result.getLong("id");
                String title = result.getString("title");
                ShoppingListItem rowItem = new ShoppingListItem(id, title);
                allItems.add(rowItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(result, statement, connection);
        }
        return allItems;
    }

    @Override
    public ShoppingListItem getItem(long itemId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        ShoppingListItem found = null;
        try {
            connection = connect();
            statement = connection.prepareStatement("select * from ShoppingListItem where id = ?");
            statement.setLong(1, itemId);

            result = statement.executeQuery();
            if (result.next()) {
                long id = result.getLong("id");
                String title = result.getString("title");

                found = new ShoppingListItem(id, title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(result, statement, connection);
        }
        return found;
    }

    @Override
    public boolean addItem(ShoppingListItem newItem) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = connect();
            statement = connection.prepareStatement("insert into ShoppingListItem (title) values (?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newItem.getTitle());

            int rows = statement.executeUpdate();

            if (rows == 1) {
                long generatedKey = statement.getGeneratedKeys().getLong(1);
                newItem.setId(generatedKey);

                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, connection);
        }
        return success;
    }

    @Override
    public boolean removeItem(ShoppingListItem item) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = connect();
            statement = connection.prepareStatement("delete from ShoppingListItem where id = ?");
            statement.setLong(1, item.getId());

            int rows = statement.executeUpdate();
            if (rows == 1) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(statement, connection);
        }
        return success;
    }

    protected Connection connect() throws SQLException {
        if (JDBC_URL == null) {
            throw new RuntimeException("JDBC_DATABASE_URL environment variable not found");
        } else {
            return DriverManager.getConnection(JDBC_URL);
        }
    }

    /*
     * ResultSet, PreparedStatement and Connection each implement the AutoCloseable
     * interface and we can close them identically.
     * 
     * See https://www.baeldung.com/java-varargs for more information about the
     * varargs syntax used for accepting any number of parameters that are instances
     * of the same interface or superclass.
     */
    private static void closeResources(AutoCloseable... sqlResources) {
        for (AutoCloseable a : sqlResources) {
            if (a != null) {
                try {
                    a.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}