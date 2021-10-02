package com.grayberry.grayberry.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grayberry.grayberry.models.Role;
import com.grayberry.grayberry.models.Session;
import com.grayberry.grayberry.repositories.AccountRepository;
import com.grayberry.grayberry.repositories.SessionRepository;

@Component
public class PosterOnlyFilter implements Filter
{
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String sessionToken = httpRequest.getHeader("Authorization").split(" ")[1];
        Session session = sessionRepository.getSessionInfoFromToken(sessionToken);
        
        if (session != null)
        {
            int userId = session.getUserId();
            
            if (accountRepository.getAccountByUserId(userId).getRoleId() <= Role.POSTER)
            {
                chain.doFilter(httpRequest, response);
            }
            
            else
            {
                ((HttpServletResponse) response).sendError(403, "You are not authorized to create a post.");
            }
        }
        
        else
        {
            ((HttpServletResponse) response).sendError(401, "Invalid token.");
        }
    }
}
