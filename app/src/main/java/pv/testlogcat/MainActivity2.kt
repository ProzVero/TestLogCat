package pv.testlogcat

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("MainActivity2","start")
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this))
        setContentView(R.layout.activity_main2)

        var str = intent.getStringExtra("error")
        if (str=="" || str==null){
            str = "llklkjdfskljskljfkljdfskljdklsfjdklsfjdksfjkdsjfkdsjfkldjsk"
        }
        findViewById<TextView>(R.id.txt).text = str

        findViewById<Button>(R.id.buttom).setOnClickListener {
            try {
                val toNumber = "+6282346455004"

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+str)
                startActivity(intent)
                /*val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$toNumber?body="))
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)*/
            }catch (e : Exception){
                e.printStackTrace()
                Toast.makeText(this,"it may be you dont have whats app", Toast.LENGTH_LONG).show();
            }
        }

        fun isPackageInstalled(context: Context, packageName: String): Boolean {
            return try {
                context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                true
            } catch (ex: Exception) {
                false
            }
        }

    }


}