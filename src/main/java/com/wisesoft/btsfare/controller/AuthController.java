package com.wisesoft.btsfare.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wisesoft.btsfare.component.JwtUtil;
import com.wisesoft.btsfare.modal.AuthResponse;
import com.wisesoft.btsfare.modal.LoginRequest;
import com.wisesoft.btsfare.modal.RegisterRequest;
import com.wisesoft.btsfare.service.AuthService;
import com.wisesoft.btsfare.service.CustomUserDetailsService;
import com.wisesoft.btsfare.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil, UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse auth = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", auth.getRefreshToken())
                .httpOnly(true)
                .secure(true) // ✅ ถ้าใช้ HTTPS
                .path("/api/auth/refresh") // จำกัดเฉพาะ endpoint
                .maxAge(7 * 24 * 60 * 60) // 7 วัน
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new AuthResponse(auth.getToken(), null));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // ลบ refreshToken cookie โดยทำให้หมดอายุ
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true) // ถ้าใช้ HTTPS
                .path("/api/auth/refresh")
                .maxAge(0) // ✅ ทำให้หมดอายุทันที
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build(); // ไม่มี cookie
        }

        try {
            return ResponseEntity.ok(authService.refreshToken(refreshToken));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body(new AuthResponse(null, "Refresh token expired"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new AuthResponse(null, "Invalid token"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }
}
