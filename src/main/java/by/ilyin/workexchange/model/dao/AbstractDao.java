package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.entity.BaseEntity;

import java.sql.Connection;

public class AbstractDao<T extends BaseEntity> {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }



}
