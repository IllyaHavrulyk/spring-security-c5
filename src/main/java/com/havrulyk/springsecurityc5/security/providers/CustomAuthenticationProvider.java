package com.havrulyk.springsecurityc5.security.providers;

import com.havrulyk.springsecurityc5.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Value("${key}")
  private String key;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String requestKey = authentication.getName();
    if(requestKey.equals(key)){
      return new CustomAuthentication(null, null, null);
    }else{
      throw new BadCredentialsException("Bad credentials!");
    }
  }

  @Override
  public boolean supports(Class<?> authType) {
    return UsernamePasswordAuthenticationToken.class.equals(authType);
  }
}
