package com.focusenterprise.data.repositories

import com.focusenterprise.data.Customer
import com.focusenterprise.data.CustomerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CustomerRepository(private val customerDao: CustomerDao) {

    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    fun getCustomerById(id: Int): Flow<Customer> {
        return customerDao.getCustomerById(id)
    }

    suspend fun insert(customer: Customer) {
        withContext(Dispatchers.IO) {
            customerDao.insert(customer)
        }
    }

    suspend fun update(customer: Customer) {
        withContext(Dispatchers.IO) {
            customerDao.update(customer)
        }
    }

    suspend fun delete(customer: Customer) {
        withContext(Dispatchers.IO) {
            customerDao.delete(customer)
        }
    }
}
