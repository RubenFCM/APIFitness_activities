package dao;

import entidades.Country;
import entidades.Diet;

import java.util.List;

public interface CountryDAOInterface {

    Country searchById(Long id);
    Country createCountry(Country country);
    List<Country> showAll();
    List<Country> showAll(int page, int size);
    Country updateCountryByID(Country country);
    boolean deleteCountryByID(Long id);
    Long totalCountries();
}
