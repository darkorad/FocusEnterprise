package com.focusenterprise.data.repositories

import com.focusenterprise.data.StockItem
import com.focusenterprise.data.StockItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StockRepository(private val stockItemDao: StockItemDao) {

    val allStockItems: Flow<List<StockItem>> = stockItemDao.getAllStockItems()

    fun getStockItemById(id: Int): Flow<StockItem> {
        return stockItemDao.getStockItemById(id)
    }

    suspend fun insert(stockItem: StockItem) {
        withContext(Dispatchers.IO) {
            stockItemDao.insert(stockItem)
        }
    }

    suspend fun update(stockItem: StockItem) {
        withContext(Dispatchers.IO) {
            stockItemDao.update(stockItem)
        }
    }

    suspend fun delete(stockItem: StockItem) {
        withContext(Dispatchers.IO) {
            stockItemDao.delete(stockItem)
        }
    }

    suspend fun reduceStock(stockId: Int, quantity: Int) {
        withContext(Dispatchers.IO) {
            stockItemDao.reduceStock(stockId, quantity)
        }
    }
}
