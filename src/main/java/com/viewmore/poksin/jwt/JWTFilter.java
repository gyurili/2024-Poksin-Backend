package com.viewmore.poksin.jwt;

import com.viewmore.poksin.code.ErrorCode;
import com.viewmore.poksin.entity.MainUserEntity;
import com.viewmore.poksin.util.TokenErrorResponse;
import com.viewmore.poksin.dto.user.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            TokenErrorResponse.sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // access token을 입력했는지 확인
        String type = jwtUtil.getType(token);
        if (!type.equals("accessToken")) {
            TokenErrorResponse.sendErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);

        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        MainUserEntity user = MainUserEntity.signupBuilder()
                .username(username)
                .password("temppassword")
                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
