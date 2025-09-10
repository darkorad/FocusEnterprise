package com.focusenterprise.data;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0014\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\b0\u0007H\'J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\n\u001a\u00020\u000bH\'J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\r\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bH\'J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0010"}, d2 = {"Lcom/focusenterprise/data/StockItemDao;", "", "delete", "", "stockItem", "Lcom/focusenterprise/data/StockItem;", "getAllStockItems", "Lkotlinx/coroutines/flow/Flow;", "", "getStockItemById", "stockId", "", "insert", "reduceStock", "soldQty", "update", "app_debug"})
@androidx.room.Dao
public abstract interface StockItemDao {
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insert(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.StockItem stockItem);
    
    @androidx.room.Update
    public abstract void update(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.StockItem stockItem);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.focusenterprise.data.StockItem stockItem);
    
    @androidx.room.Query(value = "SELECT * FROM stock_items ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.focusenterprise.data.StockItem>> getAllStockItems();
    
    @androidx.room.Query(value = "SELECT * FROM stock_items WHERE stockId = :stockId")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<com.focusenterprise.data.StockItem> getStockItemById(int stockId);
    
    @androidx.room.Query(value = "UPDATE stock_items SET quantity = quantity - :soldQty WHERE stockId = :stockId")
    public abstract int reduceStock(int stockId, int soldQty);
}