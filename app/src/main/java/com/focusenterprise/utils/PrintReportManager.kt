package com.focusenterprise.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import com.focusenterprise.viewmodel.SoldArticleSummary
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PrintReportManager {
    
    companion object {
        fun printMonthlyReport(
            context: Context,
            month: Calendar,
            earnings: Double,
            expenses: Double,
            soldArticles: List<SoldArticleSummary>
        ) {
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val jobName = "Focus Enterprise Report - ${SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(month.time)}"
            
            val printAdapter = ReportPrintDocumentAdapter(
                context,
                month,
                earnings,
                expenses,
                soldArticles
            )
            
            printManager.print(jobName, printAdapter, null)
        }
    }
    
    private class ReportPrintDocumentAdapter(
        private val context: Context,
        private val month: Calendar,
        private val earnings: Double,
        private val expenses: Double,
        private val soldArticles: List<SoldArticleSummary>
    ) : PrintDocumentAdapter() {
        
        private val PAGE_WIDTH = 595 // A4 width in points
        private val PAGE_HEIGHT = 842 // A4 height in points
        private val MARGIN = 50
        private val LINE_HEIGHT = 20
        
        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback.onLayoutCancelled()
                return
            }
            
            val info = PrintDocumentInfo.Builder("report.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(1)
                .build()
            
            callback.onLayoutFinished(info, true)
        }
        
        override fun onWrite(
            pages: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback
        ) {
            try {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas
                
                drawReportContent(canvas)
                
                pdfDocument.finishPage(page)
                
                val fileOutputStream = FileOutputStream(destination?.fileDescriptor)
                pdfDocument.writeTo(fileOutputStream)
                pdfDocument.close()
                fileOutputStream.close()
                
                callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                
            } catch (e: IOException) {
                callback.onWriteFailed(e.toString())
            }
        }
        
        private fun drawReportContent(canvas: Canvas) {
            val paint = Paint().apply {
                color = Color.BLACK
                textSize = 16f
                isAntiAlias = true
            }
            
            val titlePaint = Paint().apply {
                color = Color.BLACK
                textSize = 24f
                isAntiAlias = true
                isFakeBoldText = true
            }
            
            val headerPaint = Paint().apply {
                color = Color.BLACK
                textSize = 18f
                isAntiAlias = true
                isFakeBoldText = true
            }
            
            var yPosition = MARGIN + 30
            
            // Title
            canvas.drawText("Focus Enterprise - Monthly Report", MARGIN.toFloat(), yPosition.toFloat(), titlePaint)
            yPosition += 40
            
            // Month
            val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            canvas.drawText("Report for: ${monthFormat.format(month.time)}", MARGIN.toFloat(), yPosition.toFloat(), headerPaint)
            yPosition += 40
            
            // Financial Summary
            canvas.drawText("Financial Summary", MARGIN.toFloat(), yPosition.toFloat(), headerPaint)
            yPosition += 30
            
            canvas.drawText("Total Earnings: RSD ${"%.2f".format(earnings)}", MARGIN.toFloat(), yPosition.toFloat(), paint)
            yPosition += LINE_HEIGHT
            
            canvas.drawText("Total Expenses: RSD ${"%.2f".format(expenses)}", MARGIN.toFloat(), yPosition.toFloat(), paint)
            yPosition += LINE_HEIGHT
            
            val netEarnings = earnings - expenses
            val netPaint = Paint().apply {
                color = if (netEarnings >= 0) Color.GREEN else Color.RED
                textSize = 16f
                isAntiAlias = true
                isFakeBoldText = true
            }
            canvas.drawText("Net Earnings: RSD ${"%.2f".format(netEarnings)}", MARGIN.toFloat(), yPosition.toFloat(), netPaint)
            yPosition += 40
            
            // Sold Articles
            if (soldArticles.isNotEmpty()) {
                canvas.drawText("Sold Articles Summary", MARGIN.toFloat(), yPosition.toFloat(), headerPaint)
                yPosition += 30
                
                // Table headers
                canvas.drawText("Article Name", MARGIN.toFloat(), yPosition.toFloat(), paint)
                canvas.drawText("Quantity", (MARGIN + 250).toFloat(), yPosition.toFloat(), paint)
                canvas.drawText("Total Value", (MARGIN + 350).toFloat(), yPosition.toFloat(), paint)
                yPosition += LINE_HEIGHT + 5
                
                // Draw line under headers
                canvas.drawLine(
                    MARGIN.toFloat(),
                    yPosition.toFloat(),
                    (PAGE_WIDTH - MARGIN).toFloat(),
                    yPosition.toFloat(),
                    paint
                )
                yPosition += 10
                
                // Article data
                for (article in soldArticles) {
                    if (yPosition > PAGE_HEIGHT - MARGIN - 100) break // Prevent overflow
                    
                    canvas.drawText(article.name, MARGIN.toFloat(), yPosition.toFloat(), paint)
                    canvas.drawText(article.totalQuantity.toString(), (MARGIN + 250).toFloat(), yPosition.toFloat(), paint)
                    canvas.drawText("RSD ${"%.2f".format(article.totalValue)}", (MARGIN + 350).toFloat(), yPosition.toFloat(), paint)
                    yPosition += LINE_HEIGHT
                }
            }
            
            // Footer
            yPosition = PAGE_HEIGHT - MARGIN
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            canvas.drawText("Generated on: ${dateFormat.format(Date())}", MARGIN.toFloat(), yPosition.toFloat(), paint)
        }
    }
}