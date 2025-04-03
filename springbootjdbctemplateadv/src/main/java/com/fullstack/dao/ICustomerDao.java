package com.fullstack.dao;

import com.fullstack.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerDao {

    void signUp(Customer customer);

    boolean signIn(String custEmailId, String custPassword);

    void saveAll(List<Customer> customers);

    Optional<Customer> findById(int custId);

    List<Customer> findAll();

    void update(int custId, Customer customer);

    void deleteById(int custId);

    void deleteAll();
}
