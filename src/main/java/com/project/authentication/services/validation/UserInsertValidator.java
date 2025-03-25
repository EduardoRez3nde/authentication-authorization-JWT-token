package com.project.authentication.services.validation;

import com.project.authentication.dtos.RegisterDTO;
import com.project.authentication.dtos.exceptionsDTO.FieldMessageDTO;
import com.project.authentication.entities.User;
import com.project.authentication.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, RegisterDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterDTO dto, ConstraintValidatorContext context) {

        List<FieldMessageDTO> errors = new ArrayList<>();
        User user = userRepository.findByUsername(dto.username());

        if (user != null && user.getUsername().equalsIgnoreCase(dto.username()))
            errors.add(new FieldMessageDTO("email", "Email already exists"));

        // Inserir na lista do bean validation
        for (FieldMessageDTO e : errors) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return errors.isEmpty();
    }
}
