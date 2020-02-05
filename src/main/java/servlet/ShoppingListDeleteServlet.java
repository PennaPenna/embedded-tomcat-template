package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.JDBCShoppingListItemDao;
import database.ShoppingListItemDao;
import model.ShoppingListItem;

@WebServlet("/list/delete")
public class ShoppingListDeleteServlet extends HttpServlet {

    private ShoppingListItemDao dao = new JDBCShoppingListItemDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        ShoppingListItem item = dao.getItem(id);
        dao.removeItem(item);

        /*
         * "Post/Redirect/Get (PRG) is a web development design pattern that lets the
         * page shown after a form submission be reloaded, shared, or bookmarked without
         * ill effects, such as submitting the form another time."
         * https://en.wikipedia.org/wiki/Post/Redirect/Get
         */
        resp.sendRedirect("/list");
    }
}
