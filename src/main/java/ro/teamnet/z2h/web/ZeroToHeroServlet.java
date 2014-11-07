package ro.teamnet.z2h.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ZeroToHeroServlet extends HttpServlet {

    private String handleRequest (HttpServletRequest req){
        String response;

        response = "Hello <b>"+ req.getParameter("firstName")+" "+req.getParameter("lastName")+ "</b>! Enjoy Zero To Hero!!! <br>";


        return response;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         resp.setContentType("text/html");
         resp.getWriter().write(handleRequest(req));


    }

}
