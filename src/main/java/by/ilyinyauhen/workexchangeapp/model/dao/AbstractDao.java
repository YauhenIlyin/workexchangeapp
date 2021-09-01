package by.ilyinyauhen.workexchangeapp.model.dao;

import by.ilyinyauhen.workexchangeapp.model.entity.BaseEntity;

import java.sql.Connection;

public class AbstractDao<T extends BaseEntity> {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
