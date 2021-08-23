package by.ilyinyauhen.workexchangeapp.model.entity;

import java.time.LocalDate;
import java.util.Arrays;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private LocalDate registrationDate;
    private LocalDate lastActivityDate;
    private String email;
    private String mobileNumber;
    private String login;
    private char[] password;
    private long roleId;
    private long accountStatusId;

    public User(long id, String firstName, String lastName, LocalDate registrationDate, LocalDate lastActivityDate, String email, String mobileNumber, String login, char[] password, long roleId, long accountStatusId) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.lastActivityDate = lastActivityDate;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.accountStatusId = accountStatusId;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getAccountStatusId() {
        return accountStatusId;
    }

    public void setAccountStatusId(long accountStatusId) {
        this.accountStatusId = accountStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (this.getId() != user.getId()) return false;
        if (roleId != user.roleId) return false;
        if (accountStatusId != user.accountStatusId) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!registrationDate.equals(user.registrationDate)) return false;
        if (!lastActivityDate.equals(user.lastActivityDate)) return false;
        if (!email.equals(user.email)) return false;
        if (!mobileNumber.equals(user.mobileNumber)) return false;
        if (!login.equals(user.login)) return false;
        return Arrays.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = (int) this.getId(); //todo
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + registrationDate.hashCode();
        result = 31 * result + lastActivityDate.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + mobileNumber.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (int) (accountStatusId ^ (accountStatusId >>> 32));
        return result;
    }

    @Override
    public String toString() { //todo
        return "User{" +
                "id='" + this.getId() + '\'' +
                ",firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationDate=" + registrationDate +
                ", lastActivityDate=" + lastActivityDate +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                ", roleId=" + roleId +
                ", accountStatusId=" + accountStatusId +
                '}';
    }
}
