package com.fullstack.dao;

import com.fullstack.model.Customer;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDaoImpl implements ICustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Customer customer(ResultSet resultSet, int numRow) throws SQLException {
        return Customer.builder().custId(resultSet.getInt(1)).custName(resultSet.getString(2)).custAddress(resultSet.getString(3)).custContactNumber(resultSet.getLong(4)).custAccBalance(resultSet.getDouble(5)).custDOB(resultSet.getDate(6)).custEmailId(resultSet.getString(7)).custPassword(resultSet.getString(8)).build();
    }

    String insertSQL = "insert into customer(custid, custname, custaddress, custcontactnumber,custaccbalance, custdob, custemailid, custpassword)values(?, ?, ?, ?, ?, ?, ?, ?)";

    String selectSQLByID = "select * from customer where custId=?";

    String selectAllSQL = "select * from customer";

    String updateSQL = "update customer set custName=?, custaddress=?, custcontactnumber=?, custaccbalance=?, custdob=?, custemailid=?, custpassword=? where custid=?";

    String deleteByIdSQL = "delete from customer where custid=?";

    String deleteAllSQL = "truncate table customer";


    @Override
    public void signUp(Customer customer) {

        jdbcTemplate.update(insertSQL, customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustContactNumber(), customer.getCustAccBalance(), customer.getCustDOB(), customer.getCustEmailId(), customer.getCustPassword());

    }

    @Override
    public boolean signIn(String custEmailId, String custPassword) {

        boolean flag = false;

        for (Customer customer : findAll()) {
            if (customer.getCustEmailId().equals(custEmailId) && customer.getCustPassword().equals(custPassword)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void saveAll(List<Customer> customers) {

        for (Customer customer : customers) {
            jdbcTemplate.update(insertSQL, customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustContactNumber(), customer.getCustAccBalance(), customer.getCustDOB(), customer.getCustEmailId(), customer.getCustPassword());
        }
    }

    @Override
    public Optional<Customer> findById(int custId) {
        return Optional.ofNullable(jdbcTemplate.query(selectSQLByID, this::customer, custId).get(0));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(selectAllSQL, this::customer);
    }

    @Override
    public void update(int custId, Customer customer) {
        jdbcTemplate.update(updateSQL, customer.getCustName(), customer.getCustAddress(), customer.getCustContactNumber(), customer.getCustAccBalance(), customer.getCustDOB(), customer.getCustEmailId(), customer.getCustPassword(), custId);

    }

    @Override
    public void deleteById(int custId) {
        jdbcTemplate.update(deleteByIdSQL, custId);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(deleteAllSQL);
    }
}
