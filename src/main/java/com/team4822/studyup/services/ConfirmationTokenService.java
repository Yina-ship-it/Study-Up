package com.team4822.studyup.services;

import com.team4822.studyup.models.authentication.ConfirmationToken;
import com.team4822.studyup.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Inject
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getConfirmationToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }

    public void deleteConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }
}
