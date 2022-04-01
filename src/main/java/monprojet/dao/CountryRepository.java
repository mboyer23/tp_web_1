package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.dto.PopulationResult;
import monprojet.entity.City;
import monprojet.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    
    /*@Query(value = "SELECT SUM(City.POPULATION) "
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
    */

    //CORRECTION
    // JPQL : formulée sur le modèle conceptuel de données
    @Query("SELECT SUM(c.population) FROM City c WHERE c.country.id = :idDuPays")
    int populationDuPaysJPQL(int idDuPays);

    // SQL : formulée sur le modèle logique de données, il faut connaître la clé étrangère
    @Query(value = "SELECT SUM(c.population) FROM City c WHERE c.country_id = :idDuPays", 
    nativeQuery = true)
    int populationDuPaysSQL(int idDuPays);

    // JPQL : formulée sur le modèle conceptuel de données, la jointure est implicite
    @Query("SELECT c.country.name AS countryName, SUM(c.population) AS populationTotale FROM City c GROUP BY countryName")
    List<PopulationResult> populationParPaysJPQL();

    // SQL : formulée sur le modèle logique de données, il faut expliciter la jointure
    @Query(value = "SELECT Country.name AS countryName, SUM(population) AS populationTotale FROM Country INNER JOIN City ON country_id = Country.id GROUP BY countryName", 
    nativeQuery = true)
    List<PopulationResult> populationParPaysSQL();
}
