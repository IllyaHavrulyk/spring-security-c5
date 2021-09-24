package com.havrulyk.springsecurityc5.security.filters;

import com.havrulyk.springsecurityc5.security.authentication.CustomAuthentication;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFilter implements Filter {

  @Autowired
  private AuthenticationManager manager;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    try {
      String authorization = httpRequest.getHeader("Authorization");
      CustomAuthentication authentication = new CustomAuthentication(authorization, null);
      Authentication result = manager.authenticate(authentication);
      if (result.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(result);
        chain.doFilter(request, response);
      } else {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

  }
}
