package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.entity.User;

public interface UserDao extends BaseDao<Long, User> {
    //todo add methods from impl

    public boolean isFreeAccountLogin(char[] login) throws DaoException;

    public boolean addUserAccount(User user, char[] login, char[] password) throws DaoException;

    public boolean activateAccount(User user) throws DaoException;

    public boolean activateAccountById(Long id) throws DaoException;

}
