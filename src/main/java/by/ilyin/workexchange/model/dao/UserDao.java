package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    //todo add methods from impl

    public Optional<User> findEntityByLogin(char[] login) throws DaoException;

    public boolean isFreeAccountLogin(char[] login) throws DaoException;

    public boolean addUserAccountWithoutPassword(User user, char[] login) throws DaoException;

    public boolean addUserAccountPasswordByLogin(char[] login, char[] password) throws DaoException;

    public boolean addUserAccountPasswordById(long id, char[] password) throws DaoException;

    public String getActivationCodeByUserLogin(char[] login) throws DaoException;

    public boolean activateAccount(User user) throws DaoException;

    public boolean activateAccountById(Long id) throws DaoException;

}
