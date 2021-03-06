package price_hook.price_hook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import price_hook.price_hook.model.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

}
