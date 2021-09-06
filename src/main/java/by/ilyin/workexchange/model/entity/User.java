package by.ilyin.workexchange.model.entity;

import java.time.LocalDateTime;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private LocalDateTime registrationDateTime;
    private LocalDateTime lastActivityDateTime;
    private String email;
    private String mobileNumber;
    private String role;
    private String accountStatus;

    public User() {
    }

    public User(long id, String firstName, String lastName, LocalDateTime registrationDateTime, LocalDateTime lastActivityDateTime, String email, String mobileNumber, String login, char[] password, String role, String accountStatus) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDateTime = registrationDateTime;
        this.lastActivityDateTime = lastActivityDateTime;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    public LocalDateTime getLastActivityDateTime() {
        return lastActivityDateTime;
    }

    public void setLastActivityDateTime(LocalDateTime lastActivityDateTime) {
        this.lastActivityDateTime = lastActivityDateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public InnerBuilder createInnerBuilder() {
        return this.new InnerBuilder();
    }

    public class InnerBuilder {

        private InnerBuilder() {
        }

        public InnerBuilder setId(long id) {
            User.this.setId(id);
            return this;
        }

        public InnerBuilder setFirstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public InnerBuilder setLastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public InnerBuilder setRegistrationDate(LocalDateTime registrationDate) {
            User.this.registrationDateTime = registrationDate;
            return this;
        }

        public InnerBuilder setLastActivityDate(LocalDateTime lastActivityDate) {
            User.this.lastActivityDateTime = lastActivityDate;
            return this;
        }

        public InnerBuilder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public InnerBuilder setMobileNumber(String mobileNumber) {
            User.this.mobileNumber = mobileNumber;
            return this;
        }

        public InnerBuilder setRole(String role) {
            User.this.role = role;
            return this;
        }

        public InnerBuilder setAccountStatus(String accountStatus) {
            User.this.accountStatus = accountStatus;
            return this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (this.getId() != user.getId()) return false;
        if (role != user.role) return false;
        if (accountStatus != user.accountStatus) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!registrationDateTime.equals(user.registrationDateTime)) return false;
        if (!lastActivityDateTime.equals(user.lastActivityDateTime)) return false;
        if (!email.equals(user.email)) return false;
        return mobileNumber.equals(user.mobileNumber);
    }

    @Override
    public int hashCode() {
        int result = (int) this.getId(); //todo
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + registrationDateTime.hashCode();
        result = 31 * result + lastActivityDateTime.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + mobileNumber.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + accountStatus.hashCode();
        return result;
    }

    @Override
    public String toString() { //todo
        return "User{" +
                "id='" + this.getId() + '\'' +
                ",firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationDate=" + registrationDateTime +
                ", lastActivityDate=" + lastActivityDateTime +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                ", accountStatus=" + accountStatus +
                '}';
    }

}
