package net.siekiera.garbageNotifier.dao;

import net.siekiera.garbageNotifier.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Eric on 04.02.2017.
 */
@Transactional
public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    @Query("SELECT u FROM UserInfo u WHERE u.phone_number = :phonenumber")
    UserInfo findUserInfoByPhoneNumber(@Param("phonenumber") String phonenumber);

    @Query("SELECT u FROM UserInfo u WHERE u.phone_number = :phonenumber")
    List<UserInfo> findUserInfoByPhoneNumberList(@Param("phonenumber") String phonenumber);

    @Query("SELECT u FROM UserInfo u WHERE u.active = true")
    List<UserInfo> findActiveUsers();
}
