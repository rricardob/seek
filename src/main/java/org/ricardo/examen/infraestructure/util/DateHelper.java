package org.ricardo.examen.infraestructure.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class DateHelper {

    @Named("getYearsOld")
     public int getYearsOld(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
