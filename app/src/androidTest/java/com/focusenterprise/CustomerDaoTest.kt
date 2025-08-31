package com.focusenterprise

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.focusenterprise.data.AppDatabase
import com.focusenterprise.data.Customer
import com.focusenterprise.data.CustomerDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CustomerDaoTest {
    private lateinit var customerDao: CustomerDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        customerDao = db.customerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCustomer() = runBlocking {
        val customer = Customer(customerId = 1, name = "Test Customer", phone = "123", email = "test@test.com", address = "123 Test St", totalDebt = 100.0)
        customerDao.insert(customer)
        val byId = customerDao.getCustomerById(1).first()
        assertEquals(byId.name, "Test Customer")
    }

    @Test
    @Throws(Exception::class)
    fun updateCustomer() = runBlocking {
        val customer = Customer(customerId = 1, name = "Test Customer", phone = "123", email = "test@test.com", address = "123 Test St", totalDebt = 100.0)
        customerDao.insert(customer)
        val updatedCustomer = customer.copy(name = "Updated Name")
        customerDao.update(updatedCustomer)
        val byId = customerDao.getCustomerById(1).first()
        assertEquals(byId.name, "Updated Name")
    }

    @Test
    @Throws(Exception::class)
    fun deleteCustomer() = runBlocking {
        val customer = Customer(customerId = 1, name = "Test Customer", phone = "123", email = "test@test.com", address = "123 Test St", totalDebt = 100.0)
        customerDao.insert(customer)
        customerDao.delete(customer)
        val allCustomers = customerDao.getAllCustomers().first()
        assertEquals(allCustomers.isEmpty(), true)
    }
}
