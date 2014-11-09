package ro.teamnet.z2h.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;



public class HelloWorldForward extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        RequestDispatcher reqD = req.getRequestDispatcher("/ro/teamnet/z2h/web/ZeroToHeroServlet");

        req.setAttribute("testAttribute", "Enjoy Z2H");

        reqD.forward(req, resp);

    }

}
