package com.example.Apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.example.Apigateway.util.JwtUtil;
import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil util;

	public static class Config {
	}

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

				try {
					String role = util.extractRolesFromToken(authHeader);
					String requestedPath = exchange.getRequest().getPath().toString();
					String method = exchange.getRequest().getMethod().name();

					if (!isAuthorized(role, requestedPath, method)) {
						return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
					}

				} catch (Exception e) {
					return handleUnauthorized(exchange.getResponse(), "Invalid token");
				}
			}
			return chain.filter(exchange);
		};
	}

	private boolean isAuthorized(String role, String path, String method) {
		if ("DOCTOR".equalsIgnoreCase(role)) {
			return path.startsWith("/appointment") || path.startsWith("/prescription") || path.startsWith("/doctor")
					|| path.startsWith("/auth/getDocInfo") || path.startsWith("/patient/getAllPatients");
		} else if ("PATIENT".equalsIgnoreCase(role)) {
			return (path.startsWith("/appointment/book") || path.startsWith("/appointment/delete")
					|| path.startsWith("/appointment/findById") || path.startsWith("/appointment/getStatus")
					|| path.startsWith("/appointment/getById") || path.startsWith("/appointment/findByPatientId")
					|| path.startsWith("/appointment/getByStatus") || path.startsWith("/appointment/getByStatusPat")
					|| path.startsWith("/appointment/getTodaysApp/") || path.startsWith("/prescription/show")
					|| path.startsWith("/prescription/getByAppointmentId}") || path.startsWith("/patient")
					|| (path.startsWith("/doctor") && method.equalsIgnoreCase("GET"))
					|| path.startsWith("/auth/getPatInfo/{id}"));
		}
		return false;
	}

	private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
		response.setStatusCode(HttpStatus.FORBIDDEN);
		return response.setComplete();
	}
}
