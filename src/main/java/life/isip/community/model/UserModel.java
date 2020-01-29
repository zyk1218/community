package life.isip.community.model;


import lombok.Data;

@Data
public class UserModel {
    private Integer id ;
    private String accountId ;
    private String name ;
    private String token ;
    private Long gmtCreate;
    private Long gmtModified ;
    private String avatarUrl;
}
