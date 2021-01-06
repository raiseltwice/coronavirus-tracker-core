package com.ct.dataapi.entitiestodelete;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Long id;
    private String countryName;
    private Double latitude;
    private Double longitude;

    @JsonBackReference
    @OneToMany(mappedBy = "country")
    private Set<State> states;

    @JsonBackReference
    @OneToMany(mappedBy = "country")
    private Set<CountryCasesPerDate> countryCasesPerDate;

    public Country(String countryName, Double latitude, Double longitude) {
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
