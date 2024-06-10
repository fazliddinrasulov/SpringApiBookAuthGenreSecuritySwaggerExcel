package uz.pdp.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.back.entity.User;
import uz.pdp.back.model.dto.LoginReqDto;
import uz.pdp.back.model.dto.TokenDto;
import uz.pdp.back.security.JwtUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public HttpEntity<?> login(@RequestBody LoginReqDto loginReqDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.username(), loginReqDto.password()));
        User user = (User) authenticate.getPrincipal();
        TokenDto tokenDto = new TokenDto(
                jwtUtils.generateToken(user),
                jwtUtils.generateRefreshToken(user)
        );
        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }
}
