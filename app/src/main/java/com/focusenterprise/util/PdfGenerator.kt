package com.focusenterprise.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.provider.MediaStore
import com.focusenterprise.data.Customer
import com.focusenterprise.data.Invoice
import com.focusenterprise.data.InvoiceItem
import java.io.FileOutputStream

object PdfGenerator {

    fun generateInvoicePdf(
        context: Context,
        invoice: Invoice,
        invoiceItems: List<InvoiceItem>,
        customer: Customer
    ) {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 page size
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()

        var yPos = 20f

        // Title
        paint.textSize = 16f
        paint.isFakeBoldText = true
        canvas.drawText("Invoice #${invoice.invoiceId}", 20f, yPos, paint)
        yPos += 30

        // Customer Info
        paint.isFakeBoldText = false
        paint.textSize = 12f
        canvas.drawText("Customer: ${customer.name}", 20f, yPos, paint)
        yPos += 15
        canvas.drawText("Address: ${customer.address ?: "N/A"}", 20f, yPos, paint)
        yPos += 15
        canvas.drawText("Date: ${android.text.format.DateFormat.format("dd/MM/yyyy", invoice.date)}", 20f, yPos, paint)
        yPos += 30

        // Table Header
        paint.isFakeBoldText = true
        canvas.drawText("Item", 20f, yPos, paint)
        canvas.drawText("Qty", 300f, yPos, paint)
        canvas.drawText("Price", 400f, yPos, paint)
        canvas.drawText("Total", 500f, yPos, paint)
        yPos += 20
        canvas.drawLine(20f, yPos, 575f, yPos, paint)
        yPos += 15
        paint.isFakeBoldText = false

        // Table Items
        for (item in invoiceItems) {
            canvas.drawText("Item ID: ${item.stockId}", 20f, yPos, paint) // In a real app, you'd look up the name
            canvas.drawText(item.quantity.toString(), 300f, yPos, paint)
            canvas.drawText(String.format("%.2f", item.unitPrice), 400f, yPos, paint)
            canvas.drawText(String.format("%.2f", item.totalPrice), 500f, yPos, paint)
            yPos += 15
        }

        // Total
        yPos += 30
        paint.isFakeBoldText = true
        paint.textSize = 14f
        canvas.drawText("Total Amount: RSD ${String.format("%.2f", invoice.totalAmount)}", 400f, yPos, paint)

        document.finishPage(page)

        // Save the file
        val fileName = "Invoice_${invoice.invoiceId}.pdf"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/FocusEnterprise")
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        uri?.let {
            resolver.openOutputStream(it).use { outputStream ->
                document.writeTo(outputStream)
            }
        }

        document.close()
    }
}
