package by.ilyin.workexchange.model.service;

import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.dao.AbstractDao;
import by.ilyin.workexchange.model.dao.EntityTransaction;
import by.ilyin.workexchange.model.dao.UserDao;
import by.ilyin.workexchange.model.dao.impl.UserDaoImpl;
import by.ilyin.workexchange.model.entity.User;

public class AccountService {

    //todo
    public boolean registerNewAccount(char[] login, char[] passwordFirst, char[] passwordSecond,

                                      String firstName, String lastName, String eMail, String mobileNumber) {
        //return dto ??
        if (!passwordFirst.equals(passwordSecond)) {
            return false;
        }
        User user = new User();
        user.createInnerBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(eMail)
                .setMobileNumber(mobileNumber);
        UserDao userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        entityTransaction.initTransaction((AbstractDao) userDao);
        try {
            userDao.isFreeAccountLogin(login);
            userDao.addUserAccount(user, login);
            userDao.addUserAccountPasswordByLogin(login, passwordFirst);
            entityTransaction.commit();
            entityTransaction.endTransaction();
        } catch (DaoException e) {
            entityTransaction.rollback();
            e.printStackTrace();//todo
        }
        return true;
    }

}
