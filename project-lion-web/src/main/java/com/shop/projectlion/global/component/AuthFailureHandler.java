package com.shop.projectlion.global.component;

import com.shop.projectlion.global.error.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = URLEncoder.encode(ErrorCode.LOGIN_ERROR.getMessage(), "UTF-8");
        setDefaultFailureUrl("/login?errorMessage="+errorMessage);

        super.onAuthenticationFailure(request,response,exception);
    }

}
