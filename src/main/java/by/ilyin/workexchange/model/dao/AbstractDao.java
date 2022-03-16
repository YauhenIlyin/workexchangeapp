package by.ilyin.workexchange.model.dao;

import by.ilyin.workexchange.model.evidence.StatementType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Hashtable;
import java.util.Iterator;

public class AbstractDao {

    private static Logger logger = LogManager.getLogger();
    protected Connection connection; //todo и тогда делаем прайват
    private Hashtable<String, Statement> currentStatementHashtable = new Hashtable<String, Statement>();
    private boolean statementAutoCloseableStatus = true;

    public void setConnection(Connection connection) {  //todo транзакция  с ним на одном уровне, мб package-private
        this.connection = connection;
    }

    public void closeStatement(Statement statement) {
        if (statement == null) {
            logger.log(Level.WARN, "Statement closing isn't possible. Statement is null.");
        } else {
            try {
                statement.close();
            } catch (SQLException cause) {
                logger.log(Level.ERROR, "Can't close the statement.", cause);
            }
        }
    }

    public void closeAllActiveStatements() {
        Iterator<Statement> statementIterator = currentStatementHashtable.elements().asIterator();
        while (statementIterator.hasNext()) {
            closeStatement(statementIterator.next());
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException cause) {
            logger.log(Level.ERROR, "Can't close the connection.", cause);
        }
    }

    public Statement getCurrentStatementInstance(String statementType, String keyWordStatement, String queryValue) throws SQLException {
        Statement statement = null;
        if (currentStatementHashtable.containsKey(keyWordStatement)) {
            statement = currentStatementHashtable.get(keyWordStatement);
        } else {
            switch (statementType) {
                case StatementType.PREPARED_STATEMENT:
                    statement = connection.prepareStatement(queryValue);
                    break;
                case StatementType.CALLABLE_STATEMENT:
                    statement = connection.prepareCall(queryValue);
                    break;
            }
            currentStatementHashtable.put(keyWordStatement, statement);
        }
        return statement;
    }

    public void activateStatementAutoCloseable() {
        statementAutoCloseableStatus = true;
    }

    public void deactivateStatementAutoCloseable() {
        statementAutoCloseableStatus = false;
    }

    public boolean getStatementAutoCloseableStatus() {
        return this.statementAutoCloseableStatus;
    }

}
