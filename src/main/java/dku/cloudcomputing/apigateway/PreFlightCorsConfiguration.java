package dku.cloudcomputing.apigateway;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

@Configuration
public class PreFlightCorsConfiguration {
    private static final String ALLOWED_HEADERS = "*";
    private static final String ALLOWED_METHODS = "GET, POST, DELETE";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_CREDENTIALS = "true";
    private static final String EXPOSE_HEADERS = "*, Authorization, authorization";

    private static final String MAX_AGE = "3600";


    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isPreFlightRequest((HttpServletRequest) request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                headers.add("Access-Control-Allow-Credentials", ALLOWED_CREDENTIALS);
                headers.add("Access-Control-Expose-Headers", EXPOSE_HEADERS);
            }

            return chain.filter(ctx);
        };
    }
}
