package uz.pdp.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.back.entity.User;
import uz.pdp.back.security.JwtUtils;
import uz.pdp.back.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/refresh")
public class RefreshController {
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @GetMapping
    public HttpEntity<?> refreshToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(jwtUtils.generateToken(user));
    }
}
