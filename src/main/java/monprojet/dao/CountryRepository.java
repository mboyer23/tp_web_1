package monprojet.dao; 
// dao = data access object
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.Country;
import monprojet.entity.City;
// This will be AUTO IMPLEMENTED by Spring 
//

public interface CountryRepository extends JpaRepository<Country, Integer> {
    
    @Query(value = "SELECT SUM(City.POPULATION) "
        + "FROM Country, City "
        + "WHERE Country.ID = :id "
        +" AND Country.ID = City.COUNTRY_ID",
        nativeQuery = true)
    public List<Integer> populationDuPays (String id);

    public interface PaysParPopulation {
        String getName();
        int getPopulation();
    }

    @Query(value = "SELECT Country.NAME, SUM(City.POPULATION) "
        + "FROM Country, City "
        + "WHERE Country.ID = City.COUNTRY_ID "
        + "GROUP BY Country.NAME",
        nativeQuery = true)
    public List<PaysParPopulation> populationDeChaquePays();
    
}
