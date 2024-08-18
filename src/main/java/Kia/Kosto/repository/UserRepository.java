package Kia.Kosto.repository;

import Kia.Kosto.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUserName(String user);
    boolean existsByUserNameAndPassword(String user,String password);
    //  delete data according to userName and password
    void deleteByUserNameAndPassword(String userName, String password);
}
