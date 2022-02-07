package pv.testlogcat

import android.app.Activity
import android.os.Build
import android.content.Intent
import android.os.Process
import android.util.Log
import pv.testlogcat.MainActivity
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.StringBuilder
import kotlin.system.exitProcess

class ExceptionHandler(private val myContext: Activity) : Thread.UncaughtExceptionHandler {
    private val LINE_SEPARATOR = "\n"
    override fun uncaughtException(thread: Thread, exception: Throwable) {
        Log.e("ExceptionHandler","start")
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))
        val errorReport = StringBuilder()
        errorReport.append("************ CAUSE OF ERROR ************\n\n")
        errorReport.append(stackTrace.toString())
        errorReport.append("\n************ DEVICE INFORMATION ***********\n")
        errorReport.append("Brand: ")
        errorReport.append(Build.BRAND)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Device: ")
        errorReport.append(Build.DEVICE)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Model: ")
        errorReport.append(Build.MODEL)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Id: ")
        errorReport.append(Build.ID)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Product: ")
        errorReport.append(LINE_SEPARATOR)
        errorReport.append(Build.PRODUCT)
        errorReport.append("\n************ FIRMWARE ************\n")
        errorReport.append("SDK: ")
        errorReport.append(Build.VERSION.SDK)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Release: ")
        errorReport.append(Build.VERSION.RELEASE)
        errorReport.append(LINE_SEPARATOR)
        errorReport.append("Incremental: ")
        errorReport.append(Build.VERSION.INCREMENTAL)
        errorReport.append(LINE_SEPARATOR)

        Log.e("ExceptionHandler","$errorReport")
        val intent = Intent(myContext, MainActivity2::class.java)
        intent.putExtra("error", errorReport.toString())
        myContext.startActivity(intent)
        Process.killProcess(Process.myPid())
        exitProcess(10)
    }
}