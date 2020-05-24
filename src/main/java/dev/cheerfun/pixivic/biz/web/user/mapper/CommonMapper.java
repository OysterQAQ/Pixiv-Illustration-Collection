package dev.cheerfun.pixivic.biz.web.user.mapper;

import dev.cheerfun.pixivic.biz.web.common.po.User;
import dev.cheerfun.pixivic.biz.web.user.po.UserInformation;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommonMapper {
    @Select({
            "SELECT IFNULL((\n" +
                    "SELECT 1\n" +
                    "FROM users\n" +
                    "WHERE username=#{username}\n" +
                    "LIMIT 1),0)",
    })
    int checkUserName(String username);

    @Select({
            "SELECT IFNULL((\n" +
                    "SELECT 1\n" +
                    "FROM users\n" +
                    "WHERE email=#{email} \n" +
                    "LIMIT 1),0)",
    })
    int checkUserEmail(String email);

    @Insert("insert into users (email, username,password,permission_level,is_ban,star,create_date) values (#{email}, #{username}, #{password}, #{permissionLevel}, #{isBan},#{star},#{createDate,typeHandler=org.apache.ibatis.type.LocalDateTimeTypeHandler})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "user_id")
    Integer insertUser(User user);

    @Select({
            " SELECT * FROM (SELECT * FROM users WHERE username= #{username} OR email=#{username})  temp where temp.PASSWORD=#{password}",
    })
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "isBan", column = "is_ban"),
            @Result(property = "permissionLevel", column = "permission_level"),
            @Result(property = "pixivAccount", column = "pixiv_account"),
            @Result(property = "pixivPassword", column = "pixiv_password"),
            @Result(property = "qqOpenId", column = "qq_open_id"),
            @Result(property = "isCheckEmail", column = "is_check_email"),
            @Result(property = "createDate", column = "create_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "updateDate", column = "update_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class)
    })
    User queryUserByusernameAndPassword(String username, String password);

    @Select({
            "SELECT * FROM users WHERE user_id= #{userId} ",
    })
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "isBan", column = "is_ban"),
            @Result(property = "permissionLevel", column = "permission_level"),
            @Result(property = "pixivAccount", column = "pixiv_account"),
            @Result(property = "pixivPassword", column = "pixiv_password"),
            @Result(property = "qqOpenId", column = "qq_open_id"),
            @Result(property = "isCheckEmail", column = "is_check_email"),
            @Result(property = "createDate", column = "create_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "updateDate", column = "update_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class)

    })
        //@Cacheable(value = "user", key = "#userId")
    User queryUserByUserId(int userId);

    @Select("SELECT * FROM users WHERE qq_open_id=#{qqOpenId}\n")
    @Results({
            @Result(property = "id", column = "user_id"),
            @Result(property = "isBan", column = "is_ban"),
            @Result(property = "permissionLevel", column = "permission_level"),
            @Result(property = "pixivAccount", column = "pixiv_account"),
            @Result(property = "pixivPassword", column = "pixiv_password"),
            @Result(property = "qqOpenId", column = "qq_open_id"),
            @Result(property = "isCheckEmail", column = "is_check_email"),
            @Result(property = "createDate", column = "create_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class),
            @Result(property = "updateDate", column = "update_date", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class)
    })
    User getUserByQQOpenId(String qqOpenId);

    @Update("update users set qq_open_id=#{qqOpenId} where user_id=#{userId}")
    Integer setQQOpenId(String qqOpenId, int userId);

    @Update("update users set avatar=#{avatar} where user_id=#{userId}")
    Integer setAvatar(String avatar, int userId);

    @Update("update users set email=#{email} , is_check_email=1 where user_id=#{userId}")
    Integer setEmail(String email, int userId);

    @Update("update users set password=#{password} where email=#{email}")
    Integer setPasswordByEmail(String password, String email);

    @Update("update users set password=#{password} where user_id=#{userId}")
    Integer setPasswordById(String password, int userId);

    @Update("update users set gender=#{gender} ,signature=#{signature},location=#{location}  where user_id=#{userId}")
    Integer updateUserInfo(int userId, Integer gender, String signature, String location);

    @Insert("REPLACE INTO user_information(user_id, phone_model, push_platform, push_unique_code) " +
            "VALUES (#{userId}, #{phoneModel}, #{pushPlatform}, #{pushUniqueCode})")
    Integer replaceIntoUserInformation(UserInformation info);

    @Select("select * from user_information where user_id = #{userId}")
    @Results({
            @Result(property = "userInformationId", column = "user_push_information_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "phoneModel", column = "phone_model"),
            @Result(property = "pushPlatform", column = "push_platform"),
            @Result(property = "pushUniqueCode", column = "push_unique_code"),
            @Result(property = "lastUpdatedTime", column = "last_updated_time", typeHandler = org.apache.ibatis.type.LocalDateTimeTypeHandler.class)
    })
    UserInformation queryUserInformation(Integer userId);
}
