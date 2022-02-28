package com.example.credittest.components;

import com.example.credittest.entities.RequestEntity;
import com.example.credittest.entities.ResponseEntity;
import com.example.credittest.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TransactionFilter implements Filter {

    private TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionFilter(TransactionsRepository transactionsRepository){
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        if(!requestWrapper.getRequestURI().contains("/api/v1/credit")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestBody = new String(requestWrapper.getContentAsByteArray());
        String responseBody = new String(responseWrapper.getContentAsByteArray());

        ResponseEntity response = new ResponseEntity(responseBody,responseWrapper.getStatus());
        RequestEntity request = new RequestEntity(new Date(),requestBody,requestWrapper.getRequestURI(),response);

        transactionsRepository.save(request);

        responseWrapper.copyBodyToResponse();

    }

    @Override
    public void destroy() {

    }


}

