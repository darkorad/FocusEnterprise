package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockItem: StockItem)

    @Update
    suspend fun update(stockItem: StockItem)

    @Delete
    suspend fun delete(stockItem: StockItem)

    @Query("SELECT * FROM stock_items ORDER BY name ASC")
    fun getAllStockItems(): Flow<List<StockItem>>

    @Query("SELECT * FROM stock_items WHERE stockId = :id")
    fun getStockItemById(id: Int): Flow<StockItem>

    @Query("UPDATE stock_items SET quantity = quantity - :soldQty WHERE stockId = :stockId")
    suspend fun reduceStock(stockId: Int, soldQty: Int)
}
