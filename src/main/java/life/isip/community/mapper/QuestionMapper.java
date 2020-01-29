package life.isip.community.mapper;

import life.isip.community.model.QuestionModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
  * @author remember
  * @date 2020/1/26 9:34
  */


@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) value (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(QuestionModel question);
}
