package by.ilyin.workexchange.model.entity;

import java.time.LocalDateTime;

/**
 * User entity class with fields
 * <b>id</b>
 * <b>firstName</b>
 * <b>lastName</b>
 * <b>registrationDateTime</b>
 * <b>lastActivityDateTime</b>
 * <b>email</b>
 * <b>mobileNumber</b>
 * <b>role</b>
 * <b>accountStatus</b>
 * corresponding to the database table <b>users</b>
 *
 * @author IlyinYauhen
 * @version 1.0
 */
public class User extends BaseEntity {
    /**
     * Constant field with optimal average size StringBuilder for toString()
     * method memory pre allocation value for User class version 1.0
     *
     * @see User#toString()
     */
    private static final int OPTIMAL_STRING_BUILDER_CAPACITY = 220; //todo можно ли так размещать

    /**
     * User id field
     */
    private long id;
    /**
     * User first name field
     */
    private String firstName;
    /**
     * User last name field
     */
    private String lastName;
    /**
     * Date and time filed of user registration
     */
    private LocalDateTime registrationDateTime;
    /**
     * Date and time field of the last user activity
     */
    private LocalDateTime lastActivityDateTime;
    /**
     * User's email address field
     */
    private String email;
    /**
     * User's mobile number field
     */
    private String mobileNumber;
    /**
     * Field with the user's role in the application
     */
    private String role;
    /**
     * User account status.
     * "Activation pending", "activated", "blocked", etc.
     */
    private String accountStatus;

    /**
     * Constructor to create an instance without parameters
     *
     * @see User#User(long, String, String, LocalDateTime, LocalDateTime, String, String, String, char[], String, String) //todo char[]
     */
    public User() {
    }

    /**
     * Constructor to create an instance with parameters
     *
     * @param id                   - user entity id
     * @param firstName            - user first name
     * @param lastName             - user last name
     * @param registrationDateTime - time and date of user registration
     * @param lastActivityDateTime - time and date of the last user activity
     * @param email                - user email
     * @param mobileNumber         - user mobile number
     * @param login                - user login
     * @param password             - user password
     * @param role                 - user role in the application
     * @param accountStatus        - user account status in the application
     */
    public User(long id, String firstName, String lastName, LocalDateTime registrationDateTime, LocalDateTime lastActivityDateTime, String email, String mobileNumber, String login, char[] password, String role, String accountStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDateTime = registrationDateTime;
        this.lastActivityDateTime = lastActivityDateTime;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    /**
     * Field value getter function {@link User#id}
     *
     * @return returns the user entity id
     */
    public long getId() {
        return id;
    }

    /**
     * User entity id setting function {@link User#id}
     *
     * @param id - user entity id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Field value getter function {@link User#firstName}
     *
     * @return returns the user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * User first name setting function {@link User#firstName}
     *
     * @param firstName - user first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Field value getter function {@link User#lastName}
     *
     * @return lastName - returns the user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * User last name setting function {@link User#lastName}
     *
     * @param lastName - user last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Field value getter function {@link User#registrationDateTime}
     *
     * @return returns the user registration date and time
     */
    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    /**
     * User registration date and time setting function {@link User#registrationDateTime}
     *
     * @param registrationDateTime - user registration date and time
     */
    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    /**
     * Field value getter function {@link User#lastActivityDateTime}
     *
     * @return returns the date of the user's last activity
     */
    public LocalDateTime getLastActivityDateTime() {
        return lastActivityDateTime;
    }

    /**
     * User last activity date and time setting function {@link User#lastActivityDateTime}
     *
     * @param lastActivityDateTime - user last activity date and time
     */
    public void setLastActivityDateTime(LocalDateTime lastActivityDateTime) {
        this.lastActivityDateTime = lastActivityDateTime;
    }

    /**
     * Field value getter function {@link User#email}
     *
     * @return returns the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * User email setting function {@link User#email}
     *
     * @param email - user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Field value getter function {@link User#mobileNumber}
     *
     * @return returns the user's mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Mobile number setting function {@link User#mobileNumber}
     *
     * @param mobileNumber - user mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Field value getter function {@link User#role}
     *
     * @return returns the user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Account role setting function {@link User#role}
     *
     * @param role - user role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Field value getter function {@link User#accountStatus}
     *
     * @return returns the user account status
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * Account status setting function {@link User#accountStatus}
     *
     * @param accountStatus - user account status
     */
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Inner class "InnerBuilder" instance creation function
     *
     * @return returns the instance of InnerBuilder
     * @see InnerBuilder
     */
    public InnerBuilder createInnerBuilder() {
        return this.new InnerBuilder(); //todo нужно ли this
    }

    /**
     * InnerBuilder inner class according to the "Builder" pattern to initialize the User class object
     *
     * @version 1.0
     */
    public class InnerBuilder {

        private InnerBuilder() {
        }

        /**
         * User id builder initialization function {@link User#id}
         *
         * @param id - user id
         */
        public InnerBuilder setId(long id) {
            User.this.setId(id);
            return this;
        }

        /**
         * User first name builder initialization function {@link User#firstName}
         *
         * @param firstName - user first name
         */
        public InnerBuilder setFirstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        /**
         * User last name builder initialization function {@link User#lastName}
         *
         * @param lastName - user last name
         */
        public InnerBuilder setLastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        /**
         * User registration date and time builder initialization function {@link User#registrationDateTime}
         *
         * @param registrationDateTime - user registration date and time
         */
        public InnerBuilder setRegistrationDate(LocalDateTime registrationDateTime) {
            User.this.registrationDateTime = registrationDateTime;
            return this;
        }

        /**
         * User last activity date and time builder initialization function {@link User#lastActivityDateTime}
         *
         * @param lastActivityDateTime - user last activity date time
         */
        public InnerBuilder setLastActivityDate(LocalDateTime lastActivityDateTime) {
            User.this.lastActivityDateTime = lastActivityDateTime;
            return this;
        }

        /**
         * User email builder initialization function {@link User#email}
         *
         * @param email - user email
         */
        public InnerBuilder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        /**
         * User mobile number builder initialization function {@link User#mobileNumber}
         *
         * @param mobileNumber - user mobile number
         */
        public InnerBuilder setMobileNumber(String mobileNumber) {
            User.this.mobileNumber = mobileNumber;
            return this;
        }

        /**
         * User role builder initialization function {@link User#role}
         *
         * @param role - user role
         */
        public InnerBuilder setRole(String role) {
            User.this.role = role;
            return this;
        }

        /**
         * User account status builder initialization function {@link User#accountStatus}
         *
         * @param accountStatus - user account status
         */
        public InnerBuilder setAccountStatus(String accountStatus) {
            User.this.accountStatus = accountStatus;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return this.getId() == user.getId() &&
                this.role == user.role &&
                this.accountStatus.equals(user.accountStatus) &&
                this.firstName.equals(user.firstName) &&
                this.lastName.equals(user.lastName) &&
                this.email.equals(user.email) &&
                this.lastActivityDateTime.equals(user.lastActivityDateTime) &&
                this.registrationDateTime.equals(user.registrationDateTime) &&
                this.mobileNumber.equals(user.mobileNumber);
    }

    @Override
    public int hashCode() {
        int result = (int) id; //todo  проверить варианти приведения long к int 0, - ?? и т.д.
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
    public String toString() {
        StringBuilder sb = new StringBuilder(OPTIMAL_STRING_BUILDER_CAPACITY);
        sb.append("User{")
                .append("id='").append(id)
                .append('\'').append(",firstName='").append(firstName)
                .append('\'').append(", lastName='").append(lastName)
                .append('\'').append(", registrationDateTime=").append(registrationDateTime)
                .append(", lastActivityDateTime=").append(lastActivityDateTime)
                .append(", email='").append(email)
                .append('\'').append(", mobileNumber='").append(mobileNumber)
                .append('\'').append(", role=").append(role)
                .append(", accountStatus=").append(accountStatus).append('}');
        return sb.toString();
    }
}
