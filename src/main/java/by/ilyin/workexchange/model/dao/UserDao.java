package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    //todo add methods from impl

    public Optional<User> findEntityByLogin(char[] login) throws DaoException;

    public boolean isFreeAccountLogin(char[] login) throws DaoException;

    public boolean addAccountWithoutPassword(User user, char[] login) throws DaoException;

    public void updateAccountPasswordByLogin(char[] login, char[] password) throws DaoException;

    public void updateAccountPasswordById(long id, char[] password) throws DaoException;

    public boolean activateAccountById(long id) throws DaoException;

    public Optional<Long> findAccountIdByActivationCode(String activationCode) throws DaoException;

    public String getActivationCodeByUserLogin(char[] login) throws DaoException;


}
