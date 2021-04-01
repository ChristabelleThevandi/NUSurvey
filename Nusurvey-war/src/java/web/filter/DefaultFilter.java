/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Chrisya
 */
@WebFilter(filterName = "DefaultFilter", urlPatterns = {"/*"})
public class DefaultFilter implements Filter {
    

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    FilterConfig filterConfig;
    
    private static final String CONTEXT_ROOT = "/Nusurvey";
    
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String requestServletPath = httpServletRequest.getServletPath();        
        
       
        if(httpSession.getAttribute("isLogin") == null)
        {
            httpSession.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean)httpSession.getAttribute("isLogin");
        
        if(!excludeLoginCheck(requestServletPath)) 
        {
            if(isLogin == true) 
            {
                chain.doFilter(request, response);
            }
            else
            {
                httpServletResponse.sendRedirect(CONTEXT_ROOT + "/index.xhtml");
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    public void destroy()
    {

    }
    
    private Boolean excludeLoginCheck(String path)
    {
        //Jangan lupa access right error page
        if(path.equals("/index.xhtml") ||
           path.equals("/registerUser.xhtml") || path.equals("/aboutUs.xhtml") ||path.equals("/contactUs.xhtml") ||
            path.startsWith("/javax.faces.resource") 
                
            )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}
