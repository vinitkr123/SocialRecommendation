import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RecommendationHome")
public class RecommendationHome extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        System.out.println("Into Recommedndation Home");
        PrintWriter pw = response.getWriter();
        
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("Content.html");
        utility.printHtml("Footer.html");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    

        String txtSearch = request.getParameter("seachdata");
        String[] myJsonData = request.getParameterValues("data[]");
        String checkbox = request.getParameter("locationenabled");
        
        System.out.println(txtSearch+"and "+checkbox);
    }
}
