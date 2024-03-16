package kg.nurtelecom.opinion.payload.user;


public class UserResponse {
    private Long id;
    private String nickname;
    private String avatar;

    public UserResponse(Long id, String nickname, String avatar) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

}
