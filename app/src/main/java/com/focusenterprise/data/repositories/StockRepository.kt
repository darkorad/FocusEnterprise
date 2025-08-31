package com.focusenterprise.data.repositories

import com.focusenterprise.data.StockItem
import com.focusenterprise.data.StockItemDao
import kotlinx.coroutines.flow.Flow

class StockRepository(private val stockItemDao: StockItemDao) {

    val allStockItems: Flow<List<StockItem>> = stockItemDao.getAllStockItems()

    fun getStockItemById(id: Int): Flow<StockItem> {
        return stockItemDao.getStockItemById(id)
    }

    suspend fun insert(stockItem: StockItem) {
        stockItemDao.insert(stockItem)
    }

    suspend fun update(stockItem: StockItem) {
        stockItemDao.update(stockItem)
    }

    suspend fun delete(stockItem: StockItem) {
        stockItemDao.delete(stockItem)
    }

    suspend fun reduceStock(stockId: Int, quantity: Int) {
        stockItemDao.reduceStock(stockId, quantity)
    }
}
