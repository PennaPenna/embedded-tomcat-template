package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/christmas")
public class ChristmasCountdownServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate today = LocalDate.now();
        LocalDate christmas = nextChristmasFrom(today);
        long days = ChronoUnit.DAYS.between(today, christmas);

        req.setAttribute("daysToChristmas", days);
        req.getRequestDispatcher("/WEB-INF/christmasCountdown.jsp").forward(req, resp);
    }

    /**
     * Returns the next Christmas eve counted from the given date.
     * 
     * @param date from which the next Christmas is counted
     * @return December 24th on either the same or next year as given date
     */
    private LocalDate nextChristmasFrom(LocalDate date) {
        // is it past December 24th?
        if (date.getMonthValue() == 12 && date.getDayOfMonth() > 24) {
            return LocalDate.of(date.getYear() + 1, 12, 24);
        } else {
            // Christmas is same year
            return LocalDate.of(date.getYear(), 12, 24);
        }
    }
}
