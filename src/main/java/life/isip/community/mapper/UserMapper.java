package life.isip.community.mapper;


import life.isip.community.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 *
 * 用户映射
 *
 */

@Mapper
public interface UserMapper {

    @Insert("insert into USER(name,token,account_id,gmt_create,gmt_modified) value (#{name},#{token},#{accountId},#{gmtCreate},#{gmtmodified})")
    void insertUser(UserModel userModel);


    @Select("select * from user where token = #{token}")
    UserModel findUserByToken(String token);
}
