package pv.testlogcat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var logString = ""
    var x = 0
    lateinit var mContext : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Thread.getDefaultUncaughtExceptionHandler(ExceptionHandler(this))
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this))
        setContentView(R.layout.activity_main)

        x = 0
        //saveLogs()
        //Integer.parseInt("sjbbdhcbdc")
        findViewById<Button>(R.id.btn_plus).setOnClickListener {
            x++
            findViewById<TextView>(R.id.textView).text = x.toString()
            Log.e(TAG,x.toString())
            Log.e(TAG,"qwertyuiop")
            readLogs()
        }

        checkPermission()
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, 10)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            10 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted

                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun readLogs() {
        try {

            try {
                Runtime.getRuntime().exec(arrayOf("logcat", "-c"))
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.inputStream)
            )
            val log = StringBuilder()
            var line: String? = ""
            while (bufferedReader.readLine().also { line = it } != null) {
                    log.append(line).append("\n")
            }
            //Log.e(TAG,"LOG---> ${log.toString()}")


            logString = log.toString()
            saveLogs("$log")
        } catch (e: IOException) {
            Log.e(TAG,e.toString())
        }
    }

    private fun saveLogs(log : String) {
        val sdCard = mContext.getExternalFilesDir(null)
        val dir = File(sdCard?.absolutePath.toString() + "/myLogcat")
        dir.mkdirs()
        val file = File(dir, "Logcat.txt")
        try {
            file.appendText(log)
        }catch (e : FileNotFoundException){
            Log.e(TAG,"$e")
        }catch (e : IOException){
            Log.e(TAG,"$e")
        }
    }

    override fun onStop() {

        readLogs()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        readLogs()
    }

}