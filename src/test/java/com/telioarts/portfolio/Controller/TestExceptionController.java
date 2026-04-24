package com.telioarts.portfolio.Controller;

import com.telioarts.portfolio.exceptions.InvalidReservationException;
import com.telioarts.portfolio.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;

public class TestExceptionController {
    @GetMapping("/test/not-found")
    public void throwNotFound() {
        throw new ResourceNotFoundException("Projet introuvable");
    }

    @GetMapping("/test/invalid-res")
    public void throwInvalid() {
        throw new InvalidReservationException("Date invalide");
    }
}
