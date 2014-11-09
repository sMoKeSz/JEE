package ro.teamnet.z2h.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ZeroToHeroServlet extends HttpServlet {

    private String handleRequest (HttpServletRequest req){
        String response;
        StringBuilder x = new StringBuilder();

        x.append("Hello <b>");
        x.append(req.getParameter("firstName"));
        x.append(" ");
        x.append(req.getParameter("lastName"));
        x.append("</b>! Enjoy Zero To Hero!!! <br>");

        response = x.toString();
        return response;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String x;

        x= handleRequest(req) +"From the Forward Servlet: "+ req.getAttribute("testAttribute");

        resp.getWriter().write(x);


    }

}
