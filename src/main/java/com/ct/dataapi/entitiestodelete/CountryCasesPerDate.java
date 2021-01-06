package com.ct.dataapi.entitiestodelete;

import com.ct.dataapi.entitiestodelete.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CountryCasesPerDate {

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private LocalDate date;
    private Integer numberOfCases;

    public CountryCasesPerDate(Country country, LocalDate date, Integer numberOfCases) {
        this.country = country;
        this.date = date;
        this.numberOfCases = numberOfCases;
    }
}
