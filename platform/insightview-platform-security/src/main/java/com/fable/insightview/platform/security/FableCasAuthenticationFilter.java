package com.fable.insightview.platform.security;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.ISecurityUserService;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.userAuthenticate.entity.UserInfoBean;
import com.fable.insightview.platform.userAuthenticate.service.IUserInfoService;

public class FableCasAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	//~ Static fields/initializers =====================================================================================

    /** Used to identify a CAS request for a stateful user agent, such as a web browser. */
    public static final String CAS_STATEFUL_IDENTIFIER = "_cas_stateful_";

    /**
     * Used to identify a CAS request for a stateless user agent, such as a remoting protocol client (e.g.
     * Hessian, Burlap, SOAP etc). Results in a more aggressive caching strategy being used, as the absence of a
     * <code>HttpSession</code> will result in a new authentication attempt on every request.
     */
    public static final String CAS_STATELESS_IDENTIFIER = "_cas_stateless_";

    /**
     * The last portion of the receptor url, i.e. /proxy/receptor
     */
    private String proxyReceptorUrl;

    /**
     * The backing storage to store ProxyGrantingTicket requests.
     */
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage;

    private String artifactParameter = ServiceProperties.DEFAULT_CAS_ARTIFACT_PARAMETER;

    private boolean authenticateAllArtifacts;

    private AuthenticationFailureHandler proxyFailureHandler = new SimpleUrlAuthenticationFailureHandler();
    
    
    private ISecurityUserService userDetailService;

    //~ Constructors ===================================================================================================

	public void setUserDetailService(ISecurityUserService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public FableCasAuthenticationFilter() {
        super("/j_spring_cas_security_check");
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    }
	
    //~ Methods ========================================================================================================

    @Override
    protected final void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        boolean continueFilterChain = proxyTicketRequest(serviceTicketRequest(request, response),request);
        
        CasAuthenticationToken casToken = (CasAuthenticationToken) authResult;
        User user = (User) casToken.getPrincipal();
        SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) userDetailService.loadUserByUsername(user.getUsername());
        if (null == sysUserInfoBeanTemp) {
        	request.getRequestDispatcher("/commonLogin/noUser").forward(request, response);
        	return;
        }

        request.getSession().setAttribute(SystemFinalValue.SESSION_DATA, sysUserInfoBeanTemp);
        
        if(!continueFilterChain) {
            super.successfulAuthentication(request, response, chain, authResult);
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException {
        // if the request is a proxy request process it and return null to indicate the request has been processed
        if(proxyReceptorRequest(request)) {
            logger.debug("Responding to proxy receptor request");
            CommonUtils.readAndRespondToProxyReceptorRequest(request, response, this.proxyGrantingTicketStorage);
            return null;
        }

        final boolean serviceTicketRequest = serviceTicketRequest(request, response);
        final String username = serviceTicketRequest ? CAS_STATEFUL_IDENTIFIER : CAS_STATELESS_IDENTIFIER;
        String password = obtainArtifact(request);

        if (password == null) {
            logger.debug("Failed to obtain an artifact (cas ticket)");
            password = "";
        }

        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * If present, gets the artifact (CAS ticket) from the {@link HttpServletRequest}.
     * @param request
     * @return if present the artifact from the {@link HttpServletRequest}, else null
     */
    protected String obtainArtifact(HttpServletRequest request) {
        return request.getParameter(artifactParameter);
    }

    /**
     * Overridden to provide proxying capabilities.
     */
    @Override
    protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final boolean serviceTicketRequest = serviceTicketRequest(request, response);
        final boolean result = serviceTicketRequest || proxyReceptorRequest(request) || (proxyTicketRequest(serviceTicketRequest, request));
        if(logger.isDebugEnabled()) {
            logger.debug("requiresAuthentication = "+result);
        }
        return result;
    }

    /**
     * Sets the {@link AuthenticationFailureHandler} for proxy requests.
     * @param proxyFailureHandler
     */
    public final void setProxyAuthenticationFailureHandler(
            AuthenticationFailureHandler proxyFailureHandler) {
        Assert.notNull(proxyFailureHandler,"proxyFailureHandler cannot be null");
        this.proxyFailureHandler = proxyFailureHandler;
    }

    /**
     * Wraps the {@link AuthenticationFailureHandler} to distinguish between
     * handling proxy ticket authentication failures and service ticket
     * failures.
     */
    @Override
    public final void setAuthenticationFailureHandler(
            AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(new CasAuthenticationFailureHandler(failureHandler));
    }

    public final void setProxyReceptorUrl(final String proxyReceptorUrl) {
        this.proxyReceptorUrl = proxyReceptorUrl;
    }

    public final void setProxyGrantingTicketStorage(
            final ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
        this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
    }

    public final void setServiceProperties(final ServiceProperties serviceProperties) {
        this.artifactParameter = serviceProperties.getArtifactParameter();
        this.authenticateAllArtifacts = serviceProperties.isAuthenticateAllArtifacts();
    }

    /**
     * Indicates if the request is elgible to process a service ticket. This method exists for readability.
     * @param request
     * @param response
     * @return
     */
    private boolean serviceTicketRequest(final HttpServletRequest request, final HttpServletResponse response) {
        boolean result = super.requiresAuthentication(request, response);
        if(logger.isDebugEnabled()) {
            logger.debug("serviceTicketRequest = "+result);
        }
        return result;
    }

    /**
     * Indicates if the request is elgible to process a proxy ticket.
     * @param request
     * @return
     */
    private boolean proxyTicketRequest(final boolean serviceTicketRequest, final HttpServletRequest request) {
        if(serviceTicketRequest) {
            return false;
        }
        final boolean result = authenticateAllArtifacts && obtainArtifact(request) != null && !authenticated();
        if(logger.isDebugEnabled()) {
            logger.debug("proxyTicketRequest = "+result);
        }
        return result;
    }

    /**
     * Determines if a user is already authenticated.
     * @return
     */
    private boolean authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }
    /**
     * Indicates if the request is elgible to be processed as the proxy receptor.
     * @param request
     * @return
     */
    private boolean proxyReceptorRequest(final HttpServletRequest request) {
        final String requestUri = request.getRequestURI();
        final boolean result = proxyReceptorConfigured() && requestUri.endsWith(this.proxyReceptorUrl);
        if(logger.isDebugEnabled()) {
            logger.debug("proxyReceptorRequest = "+result);
        }
        return result;
    }

    /**
     * Determines if the {@link CasAuthenticationFilter} is configured to handle the proxy receptor requests.
     *
     * @return
     */
    private boolean proxyReceptorConfigured() {
        final boolean result = this.proxyGrantingTicketStorage != null && !CommonUtils.isEmpty(this.proxyReceptorUrl);
        if(logger.isDebugEnabled()) {
            logger.debug("proxyReceptorConfigured = "+result);
        }
        return result;
    }

    /**
     * A wrapper for the AuthenticationFailureHandler that will flex the {@link AuthenticationFailureHandler} that is used. The value
     * {@link CasAuthenticationFilter#setProxyAuthenticationFailureHandler(AuthenticationFailureHandler) will be used for proxy requests
     * that fail. The value {@link CasAuthenticationFilter#setAuthenticationFailureHandler(AuthenticationFailureHandler)} will be used for
     * service tickets that fail.
     *
     * @author Rob Winch
     */
    private class CasAuthenticationFailureHandler implements AuthenticationFailureHandler {
        private final AuthenticationFailureHandler serviceTicketFailureHandler;
        public CasAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
            Assert.notNull(failureHandler,"failureHandler");
            this.serviceTicketFailureHandler = failureHandler;
        }
        public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException exception) throws IOException,
                ServletException {
            if(serviceTicketRequest(request, response)) {
                serviceTicketFailureHandler.onAuthenticationFailure(request, response, exception);
            }else {
                proxyFailureHandler.onAuthenticationFailure(request, response, exception);
            }
        }
    }

	
}
