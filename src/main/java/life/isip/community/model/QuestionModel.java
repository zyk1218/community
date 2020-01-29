package life.isip.community.model;

import lombok.Data;

/**
  * @author remember
  * @date 2020/1/26 9:44
  */

@Data
public class QuestionModel {
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
}
