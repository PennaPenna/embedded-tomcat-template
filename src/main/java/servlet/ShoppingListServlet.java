package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.JDBCShoppingListItemDao;
import database.ShoppingListItemDao;
import model.ShoppingListItem;

@WebServlet({ "", "/list" })
public class ShoppingListServlet extends HttpServlet {

    private ShoppingListItemDao dao = new JDBCShoppingListItemDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<ShoppingListItem> allItems = dao.getAllItems();

        req.setAttribute("allItems", allItems);
        req.getRequestDispatcher("/WEB-INF/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");

        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Missing title from POST request");
        }

        ShoppingListItem newItem = new ShoppingListItem(title);
        dao.addItem(newItem);

        /*
         * "Post/Redirect/Get (PRG) is a web development design pattern that lets the
         * page shown after a form submission be reloaded, shared, or bookmarked without
         * ill effects, such as submitting the form another time."
         * https://en.wikipedia.org/wiki/Post/Redirect/Get
         */
        resp.sendRedirect("/");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        ShoppingListItem item = dao.getItem(id);
        boolean success = dao.removeItem(item);

        if (success) {
            // see https://en.wikipedia.org/wiki/Post/Redirect/Get
            resp.getWriter().print("success");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().print("not found");
        }
    }
}
