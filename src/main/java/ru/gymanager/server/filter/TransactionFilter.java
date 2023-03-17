package ru.gymanager.server.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Логирует запрос и ответ на сервер
 * TODO добавить UUID для всех логов. Генерироваться id должен здесь.
 */

@Slf4j
@Component
public class TransactionFilter implements Filter {
    private static final String LOG_TAG = TransactionFilter.class.getName();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info(LOG_TAG,
            String.format(
                "REQUEST (%s) [%s]::%s",
                auth.getName(),
                request.getMethod(),
                request.getServletPath()
            )
        );

        filterChain.doFilter(request, response);

        log.info(LOG_TAG,
            String.format(
                "RESPONSE (%s) %s",
                auth.getName(),
                request.getContentType()
            )
        );
    }
}
