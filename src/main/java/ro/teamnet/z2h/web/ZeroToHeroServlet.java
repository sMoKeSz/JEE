package ro.teamnet.z2h.web;


import org.apache.tomcat.util.buf.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


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
         PrintWriter out = resp.getWriter();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            out.print("<br/>Header Name: <em>" + headerName);
            String headerValue = req.getHeader(headerName);
            out.print("</em>, Header Value: <em>" + headerValue);
            out.println("</em>");
        }


        out.println("<hr/>");
        String authHeader = req.getHeader("authorization");
        String encodedValue = authHeader.split(" ")[1];
        out.println("Base64-encoded Authorization Value: <em>" + encodedValue);

        String decodedValue = Base64.base64Decode(encodedValue);

        out.println("</em><br/>Base64-decoded Authorization Value: <em>" + decodedValue);
        out.println("</em>");

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
