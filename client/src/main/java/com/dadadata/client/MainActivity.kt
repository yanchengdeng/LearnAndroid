package com.dadadata.client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import com.dyc.learnandroid.IMyAidlInterface
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var myService : IMyAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




       findViewById<TextView>(R.id.bindService).setOnClickListener {
                bindService()
       }


        findViewById<TextView>(R.id.unBindService).setOnClickListener {
            unbindService(connectedService)
        }


        findViewById<TextView>(R.id.doServiceAction).setOnClickListener {
            if (myService == null){
                findViewById<TextView>(R.id.doServiceAction).text = "myService =  null"
                return@setOnClickListener
            }
            val num1 = Math.random()
            val num2 = Random(100).nextInt()
            val result = "$num1 + $num2 = ${ myService?.add(num2,num2)}"
            findViewById<TextView>(R.id.doServiceAction).text = result
        }
    }

    private fun bindService() {
        val intent =  Intent()
        intent.setAction("com.dyc.learnandroid.MyService");
        intent.setPackage("com.dyc.learnandroid"); // server's package name
        bindService(intent, connectedService, BIND_AUTO_CREATE);
    }

    private val connectedService =object :ServiceConnection{
        /**
         * Called when a connection to the Service has been established, with
         * the [android.os.IBinder] of the communication channel to the
         * Service.
         *
         *
         * **Note:** If the system has started to bind your
         * client app to a service, it's possible that your app will never receive
         * this callback. Your app won't receive a callback if there's an issue with
         * the service, such as the service crashing while being created.
         *
         * @param name The concrete component name of the service that has
         * been connected.
         *
         * @param service The IBinder of the Service's communication channel,
         * which you can now make calls on.
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = IMyAidlInterface.Stub.asInterface(service)
        }

        /**
         * Called when a connection to the Service has been lost.  This typically
         * happens when the process hosting the service has crashed or been killed.
         * This does *not* remove the ServiceConnection itself -- this
         * binding to the service will remain active, and you will receive a call
         * to [.onServiceConnected] when the Service is next running.
         *
         * @param name The concrete component name of the service whose
         * connection has been lost.
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
        }

    }
}