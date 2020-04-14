package com.epam.exercises.sportbetting.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("player")
public class Player extends User {
    private String name;
    private String accountNumber;
    private Double balance;
    private String currency;
    private LocalDate dateOfBirth;
}
