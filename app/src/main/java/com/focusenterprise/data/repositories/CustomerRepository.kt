package com.focusenterprise.data.repositories

import com.focusenterprise.data.Customer
import com.focusenterprise.data.CustomerDao
import kotlinx.coroutines.flow.Flow

class CustomerRepository(private val customerDao: CustomerDao) {

    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    fun getCustomerById(id: Int): Flow<Customer> {
        return customerDao.getCustomerById(id)
    }

    suspend fun insert(customer: Customer) {
        customerDao.insert(customer)
    }

    suspend fun update(customer: Customer) {
        customerDao.update(customer)
    }

    suspend fun delete(customer: Customer) {
        customerDao.delete(customer)
    }
}
