package io.buji.pac4j.filter;

import io.buji.pac4j.context.ShiroSessionStore;
import io.buji.pac4j.engine.ShiroLogoutLogic;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.LogoutLogic;
import org.pac4j.core.http.adapter.HttpActionAdapter;
import org.pac4j.core.http.adapter.JEEHttpActionAdapter;
import org.pac4j.core.util.FindBest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <p>This filter handles the (application + identity provider) logout process.</p>
 *
 * @author Jerome Leleu
 * @since 3.0.0
 */
public class LogoutFilter implements Filter {

    private LogoutLogic<Object, JEEContext> logoutLogic;

    private Config config;

    private String defaultUrl;

    private String logoutUrlPattern;

    private Boolean localLogout;

    private Boolean centralLogout;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {

        final SessionStore<JEEContext> bestSessionStore = FindBest.sessionStore(null, config, ShiroSessionStore.INSTANCE);
        final HttpActionAdapter<Object, JEEContext> bestAdapter = FindBest.httpActionAdapter(null, config, JEEHttpActionAdapter.INSTANCE);
        final LogoutLogic<Object, JEEContext> bestLogic = FindBest.logoutLogic(logoutLogic, config, ShiroLogoutLogic.INSTANCE);

        final JEEContext context = new JEEContext((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, bestSessionStore);
        bestLogic.perform(context, config, bestAdapter, defaultUrl, logoutUrlPattern, localLogout, false, centralLogout);
    }

    @Override
    public void destroy() {}

    public Config getConfig() {
        return config;
    }

    public void setConfig(final Config config) {
        this.config = config;
    }

    public String getDefaultUrl() {
        return this.defaultUrl;
    }

    public void setDefaultUrl(final String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String getLogoutUrlPattern() {
        return logoutUrlPattern;
    }

    public void setLogoutUrlPattern(String logoutUrlPattern) {
        this.logoutUrlPattern = logoutUrlPattern;
    }

    public LogoutLogic<Object, JEEContext> getLogoutLogic() {
        return logoutLogic;
    }

    public void setLogoutLogic(final LogoutLogic<Object, JEEContext> logoutLogic) {
        this.logoutLogic = logoutLogic;
    }

    public Boolean getLocalLogout() {
        return localLogout;
    }

    public void setLocalLogout(final Boolean localLogout) {
        this.localLogout = localLogout;
    }

    public Boolean getCentralLogout() {
        return centralLogout;
    }

    public void setCentralLogout(final Boolean centralLogout) {
        this.centralLogout = centralLogout;
    }
}
