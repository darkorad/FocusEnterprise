package com.focusenterprise.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.provider.MediaStore;
import com.focusenterprise.data.Customer;
import com.focusenterprise.data.Invoice;
import com.focusenterprise.data.InvoiceItem;
import java.io.FileOutputStream;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r\u00a8\u0006\u000e"}, d2 = {"Lcom/focusenterprise/util/PdfGenerator;", "", "()V", "generateInvoicePdf", "", "context", "Landroid/content/Context;", "invoice", "Lcom/focusenterprise/data/Invoice;", "invoiceItems", "", "Lcom/focusenterprise/data/InvoiceItem;", "customer", "Lcom/focusenterprise/data/Customer;", "app_debug"})
public final class PdfGenerator {
    @org.jetbrains.annotations.NotNull
    public static final com.focusenterprise.util.PdfGenerator INSTANCE = null;
    
    private PdfGenerator() {
        super();
    }
    
    public final void generateInvoicePdf(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Invoice invoice, @org.jetbrains.annotations.NotNull
    java.util.List<com.focusenterprise.data.InvoiceItem> invoiceItems, @org.jetbrains.annotations.NotNull
    com.focusenterprise.data.Customer customer) {
    }
}