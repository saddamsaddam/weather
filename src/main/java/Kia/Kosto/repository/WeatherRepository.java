package Kia.Kosto.repository;

import Kia.Kosto.model.LikedCity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("weatherRepository")
public interface WeatherRepository extends CrudRepository<LikedCity, Long> {
    LikedCity findByCityNameAndUserName(String cityName, String userName);
    List<LikedCity> findByUserName(String userName);

    boolean existsByUserNameAndCityName(String userName, String cityName);

    // update userName according to username
    @Modifying
    @Transactional
    @Query("UPDATE LikedCity lc SET lc.userName = :newUserName WHERE lc.userName = :currentUserName")
    void updateUserName(String currentUserName, String newUserName);
}
