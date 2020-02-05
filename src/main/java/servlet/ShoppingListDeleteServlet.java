package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.JDBCShoppingListItemDao;
import database.ShoppingListItemDao;
import model.ShoppingListItem;

@WebServlet("/delete")
public class ShoppingListDeleteServlet extends HttpServlet {

    private ShoppingListItemDao dao = new JDBCShoppingListItemDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        ShoppingListItem item = dao.getItem(id);
        boolean success = dao.removeItem(item);

        if (success) {
            // see https://en.wikipedia.org/wiki/Post/Redirect/Get
            resp.sendRedirect("/");
        } else {
            throw new RuntimeException("Removing item " + id + " failed.");
        }
    }
}
