package pv.testlogcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

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

    }
}