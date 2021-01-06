package com.ct.dataapi.entitiestodelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class State {

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;
    private String stateName;
    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonBackReference
    @OneToMany(mappedBy = "state")
    private Set<StateCasesPerDate> stateCasesPerDate;

    public State(String stateName, Double latitude, Double longitude, Country country) {
        this.stateName = stateName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }
}
