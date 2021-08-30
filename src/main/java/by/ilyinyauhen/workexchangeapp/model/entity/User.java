package by.ilyinyauhen.workexchangeapp.model.entity;

import java.time.LocalDate;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private LocalDate registrationDate;
    private LocalDate lastActivityDate;
    private String email;
    private String mobileNumber;
    private String role;
    private String accountStatus;

    public User(long id, String firstName, String lastName, LocalDate registrationDate, LocalDate lastActivityDate, String email, String mobileNumber, String login, char[] password, String role, String accountStatus) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.lastActivityDate = lastActivityDate;
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
        if (!registrationDate.equals(user.registrationDate)) return false;
        if (!lastActivityDate.equals(user.lastActivityDate)) return false;
        if (!email.equals(user.email)) return false;
        return mobileNumber.equals(user.mobileNumber);
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
                ", registrationDate=" + registrationDate +
                ", lastActivityDate=" + lastActivityDate +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                ", accountStatus=" + accountStatus +
                '}';
    }

}
