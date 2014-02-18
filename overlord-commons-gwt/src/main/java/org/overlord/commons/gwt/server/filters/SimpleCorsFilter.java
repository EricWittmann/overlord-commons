/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.commons.gwt.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Enables CORS (cross-origin resource sharing).
 *
 * @author eric.wittmann@redhat.com
 */
public class SimpleCorsFilter implements Filter {
    
    /**
     * Constructor.
     */
    public SimpleCorsFilter() {
    }
    
    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        if (isPreflightRequest(httpReq)) {
            httpResp.setHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
            httpResp.setHeader("Access-Control-Allow-Credentials", "true");
            httpResp.setHeader("Access-Control-Max-Age", "1800");
            httpResp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,HEAD");
            httpResp.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
            httpResp.setHeader("Access-Control-Expose-Headers", "X-Apiman-Error");
        } else {
            if (hasOriginHeader(httpReq)) {
                httpResp.setHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
                httpResp.setHeader("Access-Control-Allow-Credentials", "true");
                httpResp.setHeader("Access-Control-Expose-Headers", "X-Apiman-Error");
            }
            chain.doFilter(httpReq, httpResp);
        }
    }

    /**
     * Determines whether the request is a CORS preflight request.
     * @param httpReq
     */
    protected boolean isPreflightRequest(HttpServletRequest httpReq) {
        return isOptionsMethod(httpReq) && hasOriginHeader(httpReq);
    }

    /**
     * Returns true if it's an OPTIONS http request.
     * @param httpReq
     */
    protected boolean isOptionsMethod(HttpServletRequest httpReq) {
        return "OPTIONS".equals(httpReq.getMethod());
    }

    /**
     * Returns true if the Origin request header is present.
     * @param httpReq
     */
    protected boolean hasOriginHeader(HttpServletRequest httpReq) {
        String origin = httpReq.getHeader("Origin");
        return origin != null && origin.trim().length() > 0;
    }
    
    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
