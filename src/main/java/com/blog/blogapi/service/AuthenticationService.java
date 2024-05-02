package com.blog.blogapi.service;

import com.blog.blogapi.entities.Token;
import com.blog.blogapi.entities.Users;
import com.blog.blogapi.exceptions.ResourceNotFoundException;
import com.blog.blogapi.payloads.EmailTemplateName;
import com.blog.blogapi.payloads.RegistrationRequest;
import com.blog.blogapi.repository.RoleRepo;
import com.blog.blogapi.repository.TokenRepo;
import com.blog.blogapi.repository.UserRepo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private final String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Name: USER", 0));
        var user = Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepo.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(Users user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate Account"
        );
    }

    private String generateAndSaveActivationToken(Users user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .users(user)
                .build();
        tokenRepo.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
