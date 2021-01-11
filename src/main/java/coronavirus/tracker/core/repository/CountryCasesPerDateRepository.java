package coronavirus.tracker.core.repository;

import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CountryCasesPerDateRepository extends JpaRepository<CountryCasesPerDate, Long> {

    List<CountryCasesPerDate> findAllByCountry_CountryName(String countryName);
    Optional<CountryCasesPerDate> findOneByCountry_CountryNameAndDate(String countryName, LocalDate date);
    List<CountryCasesPerDate> findAllByCountry_CountryNameAndDateBetween(String countryName,
                                                                         LocalDate start,
                                                                         LocalDate end);

    void deleteByCountry_CountryNameAndDate(String countryName, LocalDate date);
}
