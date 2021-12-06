package com.dyc.learnandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dyc.learnandroid.coroutine.CoroutineActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName

    private var viewModel: MainViewModel? = null


    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(MainViewModel::class.java)

        viewModel?.fetchDocs()


        recyclerView = findViewById(R.id.funRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dividerDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        getDrawable(R.drawable.h_line_diviler)?.let {
            dividerDecoration.setDrawable(it)
        }

        recyclerView.addItemDecoration(dividerDecoration)
        recyclerView.adapter = AdapterFunction(this)


        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen( MyPhoneMessage(),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)

            val rquests =
            arrayOf(Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PRECISE_PHONE_STATE)
           requestPermissions( rquests,200)

    }

    data class FunctionItem<T>(
        val name: String,
        val intentActivity: Class<T>
    )


    class AdapterFunction(mainActivity: Context) : RecyclerView.Adapter<FunctionViewHolder>() {
        private var mContext = mainActivity
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {

            return FunctionViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_function_item, parent,false)
            )
        }

        override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.tvFunctionName).text =
                functionItems()[position].name
            holder.itemView.findViewById<TextView>(R.id.tvFunctionName).setOnClickListener {
               mContext.startActivity(Intent(mContext,functionItems()[position].intentActivity))
            }
        }

        override fun getItemCount(): Int {
            return functionItems().size
        }


        private fun functionItems() = mutableListOf(
            FunctionItem("协程", CoroutineActivity::class.java),
            FunctionItem("动画", CoroutineActivity::class.java),
            FunctionItem("控件", CoroutineActivity::class.java),
            FunctionItem("信号", MainActivityWIF::class.java),
        )
    }


    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    class MyPhoneMessage : PhoneStateListener() {

        override fun onSignalStrengthsChanged(signalStrength: SignalStrength?) {
//            Log.d("手机信号",signalStrength.toString())
            super.onSignalStrengthsChanged(signalStrength)
        }
    }

}