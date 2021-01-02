package com;

import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //JDBC API
        DriverManager.registerDriver(new Driver());

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");

        Statement statement = connection.createStatement();
        statement.execute("BEGIN");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM capitals");
        statement.execute("COMMIT");

        printResultSet(resultSet);

        //DATASOURCE API
        DataSource source = new PGSimpleDataSource();

        Connection connectionPg = source.getConnection("postgres", "postgres");
        Statement statementPg = connectionPg.createStatement();

        statementPg.execute("BEGIN");
        ResultSet resultSetPg = statement.executeQuery("SELECT * FROM capitals");
        statementPg.execute("COMMIT");
        printResultSet(resultSetPg);

    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int columnNumber = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnNumber; i++) {
                System.out.print(resultSet.getObject(i) + " ");
            }
            System.out.println();
        }
    }
}
