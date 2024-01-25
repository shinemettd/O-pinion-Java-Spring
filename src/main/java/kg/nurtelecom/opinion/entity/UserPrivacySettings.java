package kg.nurtelecom.opinion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_privacy_settings")
public class UserPrivacySettings extends BaseEntity{
    private boolean isFirstNameVisible;
    private boolean isLastNameVisible;
    private boolean isEmailVisible;

    private boolean isBirthDateVisible;

    @OneToOne()
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public UserPrivacySettings() {
        isFirstNameVisible = true;
        isLastNameVisible = true;
        isEmailVisible = true;
        isBirthDateVisible = true;
    }

    public boolean isFirstNameVisible() {
        return isFirstNameVisible;
    }

    public void setFirstNameVisible(boolean firstNameVisible) {
        isFirstNameVisible = firstNameVisible;
    }

    public boolean isLastNameVisible() {
        return isLastNameVisible;
    }

    public void setLastNameVisible(boolean lastNameVisible) {
        isLastNameVisible = lastNameVisible;
    }

    public boolean isEmailVisible() {
        return isEmailVisible;
    }

    public void setEmailVisible(boolean emailVisible) {
        isEmailVisible = emailVisible;
    }

    public boolean isBirthDateVisible() {
        return isBirthDateVisible;
    }

    public void setBirthDateVisible(boolean birthDateVisible) {
        isBirthDateVisible = birthDateVisible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
