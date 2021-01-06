package com.ct.dataapi.repository;

import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.StateCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StateCasesPerDateRepository extends JpaRepository<StateCasesPerDate, Long> {

    List<StateCasesPerDate> findAllByState_Country_CountryNameAndState_StateName(String countryName, String stateName);

    Optional<StateCasesPerDate> findOneByState_Country_CountryNameAndState_StateNameAndDate(String countryName,
                                                                                            String stateName,
                                                                                            LocalDate date);

    List<StateCasesPerDate> findAllByState_Country_CountryNameAndState_StateNameAndDateBetween(String countryName,
                                                                                               String stateName,
                                                                                               LocalDate start,
                                                                                               LocalDate end);

    void deleteByState_Country_CountryNameAndState_StateNameAndDate(String countryName,
                                                                    String stateName,
                                                                    LocalDate date);
}
