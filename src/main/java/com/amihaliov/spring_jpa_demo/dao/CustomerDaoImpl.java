package com.amihaliov.spring_jpa_demo.dao;

import com.amihaliov.spring_jpa_demo.model.Customer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class CustomerDaoImpl implements CustomerDao {

    private final DataSource dataSource;

    public CustomerDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer getById(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT  * FROM customers WHERE id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getCustomer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, ps, resultSet);
        }

        return null;
    }

    @Override
    public Customer getByFirstNameAndLastName(String firstName, String lastName) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection
                    .prepareStatement("SELECT  * FROM customers WHERE first_name = ? AND last_name = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return getCustomer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, ps, resultSet);
        }
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection
                    .prepareStatement("insert into customers (first_name, last_name, address, phone) values (?, ?, ?, ?)");
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.execute();

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("select last_insert_id()");

            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                return this.getById(id);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, ps, resultSet);
        }
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection
                    .prepareStatement("update customers set first_name = ?, last_name = ?, address = ?, phone = ? where id = ?");
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.setLong(5, customer.getId());
            ps.execute();

            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, ps, null);
        }
        return this.getById(customer.getId());
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("delete from customers where id = ?");
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, ps, null);
        }
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setAddress(resultSet.getString("address"));
        customer.setPhone(resultSet.getString("phone"));

        return customer;
    }

    private void closeConnections(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
