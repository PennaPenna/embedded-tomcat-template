package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/daysUntil")
public class DaysUntilServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int year = Integer.parseInt(req.getParameter("year"));
        int month = Integer.parseInt(req.getParameter("month"));
        int day = Integer.parseInt(req.getParameter("day"));

        LocalDate today = LocalDate.now();
        LocalDate toDate = LocalDate.of(year, month, day);

        long days = ChronoUnit.DAYS.between(today, toDate);

        // attributes passed to daysUntil.jsp
        req.setAttribute("days", days);
        req.setAttribute("today", today);
        req.setAttribute("toDate", toDate);

        req.getRequestDispatcher("/WEB-INF/daysUntil.jsp").forward(req, resp);
    }
}
