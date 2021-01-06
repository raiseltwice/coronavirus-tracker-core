package com.ct.dataapi.entitiestodelete;

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

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StateCasesPerDate {

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    private LocalDate date;
    private Integer numberOfCases;

    public StateCasesPerDate(State state, LocalDate date, Integer numberOfCases) {
        this.state = state;
        this.date = date;
        this.numberOfCases = numberOfCases;
    }
}
