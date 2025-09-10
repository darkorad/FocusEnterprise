package com.focusenterprise.data

import java.text.SimpleDateFormat
import java.util.*

/**
 * Data class representing a sales record for export/import operations.
 * Combines data from Invoice, InvoiceItem, Customer, and StockItem entities.
 */
data class SalesRecord(
    val date: Long,
    val customerName: String,
    val product: String,
    val quantity: Int,
    val price: Double, // Unit price
    val total: Double  // Total price (quantity * price)
) {
    /**
     * Formats the date as a readable string for Excel export
     */
    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date(date))
    }
    
    /**
     * Validates that all required fields have valid values
     */
    fun isValid(): Boolean {
        return customerName.isNotBlank() && 
               product.isNotBlank() && 
               quantity > 0 && 
               price >= 0.0 && 
               total >= 0.0
    }
    
    companion object {
        /**
         * Expected column headers for Excel export/import
         */
        val COLUMN_HEADERS = arrayOf(
            "Date",
            "CustomerName", 
            "Product",
            "Quantity",
            "Price",
            "Total"
        )
        
        /**
         * Creates a SalesRecord from Excel row data
         */
        fun fromExcelRow(
            date: String,
            customerName: String,
            product: String,
            quantity: String,
            price: String,
            total: String
        ): SalesRecord? {
            return try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val parsedDate = dateFormat.parse(date)?.time ?: System.currentTimeMillis()
                val parsedQuantity = quantity.toIntOrNull() ?: 0
                val parsedPrice = price.toDoubleOrNull() ?: 0.0
                val parsedTotal = total.toDoubleOrNull() ?: (parsedQuantity * parsedPrice)
                
                SalesRecord(
                    date = parsedDate,
                    customerName = customerName.trim(),
                    product = product.trim(),
                    quantity = parsedQuantity,
                    price = parsedPrice,
                    total = parsedTotal
                )
            } catch (e: Exception) {
                null
            }
        }
        
        /**
         * Parse SalesRecord from Excel row
         * Used during import operations
         */
        fun fromExcelRow(row: org.apache.poi.ss.usermodel.Row): SalesRecord {
            return SalesRecord(
                date = parseDate(getCellStringValue(row.getCell(0))),
                customerName = getCellStringValue(row.getCell(1)).trim(),
                product = getCellStringValue(row.getCell(2)).trim(),
                quantity = getCellNumericValue(row.getCell(3)).toInt(),
                price = getCellNumericValue(row.getCell(4)),
                total = getCellNumericValue(row.getCell(5))
            )
        }
        
        private fun getCellStringValue(cell: org.apache.poi.ss.usermodel.Cell?): String {
            return when (cell?.cellType) {
                org.apache.poi.ss.usermodel.CellType.STRING -> cell.stringCellValue
                org.apache.poi.ss.usermodel.CellType.NUMERIC -> cell.numericCellValue.toString()
                org.apache.poi.ss.usermodel.CellType.BOOLEAN -> cell.booleanCellValue.toString()
                else -> ""
            }
        }
        
        private fun getCellNumericValue(cell: org.apache.poi.ss.usermodel.Cell?): Double {
            return when (cell?.cellType) {
                org.apache.poi.ss.usermodel.CellType.NUMERIC -> cell.numericCellValue
                org.apache.poi.ss.usermodel.CellType.STRING -> {
                    try {
                        cell.stringCellValue.toDouble()
                    } catch (e: NumberFormatException) {
                        0.0
                    }
                }
                else -> 0.0
            }
        }
        
        private fun parseDate(dateString: String): Long {
            return try {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                format.parse(dateString)?.time ?: System.currentTimeMillis()
            } catch (e: Exception) {
                System.currentTimeMillis()
            }
        }
    }
}

/**
 * Summary of sales import operation
 */
data class SalesImportSummary(
    val totalRows: Int,
    val importedRows: Int,
    val skippedRows: Int,
    val errors: List<String> = emptyList()
) {
    val successRate: Double
        get() = if (totalRows > 0) (importedRows.toDouble() / totalRows) * 100 else 0.0
}