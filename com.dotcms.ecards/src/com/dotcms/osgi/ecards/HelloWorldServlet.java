package com.dotmarketing.osgi.servlet;

import com.dotmarketing.osgi.service.HelloWorld;
import com.dotmarketing.viewtools.content;
import org.osgi.util.tracker.ServiceTracker;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ECardServlet extends HttpServlet {

    private static final long serialVersionUID = 42L;
    private ServiceTracker serviceTracker;
    private ContentTool contentTool = new ContentTool();

    public ECardServlet ( ServiceTracker serviceTracker ) {
        this.serviceTracker = serviceTracker;
    }

    protected void getECards ( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws ServletException, IOException {

        httpServletResponse.setContentType( "text/html" );

        ServletOutputStream out = httpServletResponse.getOutputStream();
        
        private selectedStatus = HttpServletRequest.getParameter("eCardStatus");
        
        for ( ContentMap contentItem : contentTool.pull("+structureName:eCards +(eCards.status:*" + selectedStatus + "*", 0, "modDate desc"){
            private name = contentItem.get("name");
            private recipient = contentItem.get("private");
            private deliveryLocation = contentItem.get("deliveryLocation");
            private deliveryEmail = contentItem.get("deliveryEmail");
            private status = contentItem.get("status.selectValue");
            
            out.println("<td>" + name + "</td>");
            out.println("<td>" + recipient + "</td>");
            out.println("<td>" + deliveryLocation + "</td>");
            out.println("<td>" + deliveryEmail + "</td>");
            out.println("<td>" + status + "</td>");
        }
        
        out.println( "<html><body>" );

        HelloWorld service = (HelloWorld) serviceTracker.getService();

        if ( service != null ) {
            out.println( service.hello() );
        }

        out.println( "</body></html>" );
        out.close();
    }

}