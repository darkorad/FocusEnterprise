package com.focusenterprise.utils


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.focusenterprise.viewmodel.SoldArticleSummary
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PdfReportGenerator {
    
    companion object {
        private const val PAGE_WIDTH = 595 // A4 width in points
        private const val PAGE_HEIGHT = 842 // A4 height in points
        private const val MARGIN = 50
        private const val LINE_HEIGHT = 20
        
        fun generateMonthlyReport(
            month: Calendar,
            earnings: Double,
            expenses: Double,
            soldArticles: List<SoldArticleSummary>
        ): File? {
            try {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                var canvas = page.canvas
                
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
                
                canvas.drawText("Total Earnings: RSD ${"%,.2f".format(earnings)}", MARGIN.toFloat(), yPosition.toFloat(), paint)
                yPosition += LINE_HEIGHT
                
                canvas.drawText("Total Expenses: RSD ${"%,.2f".format(expenses)}", MARGIN.toFloat(), yPosition.toFloat(), paint)
                yPosition += LINE_HEIGHT
                
                val netEarnings = earnings - expenses
                val netPaint = Paint().apply {
                    color = if (netEarnings >= 0) Color.GREEN else Color.RED
                    textSize = 16f
                    isAntiAlias = true
                    isFakeBoldText = true
                }
                canvas.drawText("Net Earnings: RSD ${"%,.2f".format(netEarnings)}", MARGIN.toFloat(), yPosition.toFloat(), netPaint)
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
                        if (yPosition > PAGE_HEIGHT - MARGIN - 50) {
                            // Start new page if needed
                            pdfDocument.finishPage(page)
                            val newPageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 2).create()
                            val newPage = pdfDocument.startPage(newPageInfo)
                            canvas = newPage.canvas
                            yPosition = MARGIN + 30
                        }
                        
                        canvas.drawText(article.name, MARGIN.toFloat(), yPosition.toFloat(), paint)
                        canvas.drawText(article.totalQuantity.toString(), (MARGIN + 250).toFloat(), yPosition.toFloat(), paint)
                        canvas.drawText("RSD ${"%,.2f".format(article.totalValue)}", (MARGIN + 350).toFloat(), yPosition.toFloat(), paint)
                        yPosition += LINE_HEIGHT
                    }
                }
                
                // Footer
                yPosition = PAGE_HEIGHT - MARGIN
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                canvas.drawText("Generated on: ${dateFormat.format(Date())}", MARGIN.toFloat(), yPosition.toFloat(), paint)
                
                pdfDocument.finishPage(page)
                
                // Save to file
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val fileName = "FocusEnterprise_Report_${SimpleDateFormat("yyyy_MM", Locale.getDefault()).format(month.time)}.pdf"
                val file = File(downloadsDir, fileName)
                
                val fileOutputStream = FileOutputStream(file)
                pdfDocument.writeTo(fileOutputStream)
                pdfDocument.close()
                fileOutputStream.close()
                
                return file
                
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }
    }
}