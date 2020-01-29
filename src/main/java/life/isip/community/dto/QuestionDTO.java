package life.isip.community.dto;


import life.isip.community.model.UserModel;
import lombok.Data;

/**
  * @author remember
  * @date 2020/1/29 20:09
  */


@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private UserModel userModel;
}
