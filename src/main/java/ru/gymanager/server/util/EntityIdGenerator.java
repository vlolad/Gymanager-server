package ru.gymanager.server.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Генерирует id любой сущности с помощью функции БД
//Author: https://github.com/PavelProjects
public class EntityIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
                                 Object o) throws HibernateException {
        Connection connection = sharedSessionContractImplementor.connection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select generateid() as new_id");
            if (rs.next()) {
                return rs.getString("new_id");
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return null;
    }
}
