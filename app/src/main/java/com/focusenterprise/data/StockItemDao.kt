package com.focusenterprise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stockItem: StockItem)

    @Update
    fun update(stockItem: StockItem)

    @Delete
    fun delete(stockItem: StockItem)

    @Query("SELECT * FROM stock_items ORDER BY name ASC")
    fun getAllStockItems(): Flow<List<StockItem>>

    @Query("SELECT * FROM stock_items WHERE stockId = :stockId")
    fun getStockItemById(stockId: Int): Flow<StockItem>

    @Query("UPDATE stock_items SET quantity = quantity - :soldQty WHERE stockId = :stockId")
    fun reduceStock(stockId: Int, soldQty: Int): Int
}
