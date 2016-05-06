package io.github.artfly.ridetogether.server.web.dto;


public class UserDto {
    private String username;
    private Long userId;
    private String imagePath;

    public UserDto(String username, Long userId, String imagePath) {
        this.username = username;
        this.userId = userId;
        this.imagePath = imagePath;
    }

    UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
