package com.dyc.learnandroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dyc.learnandroid.coroutine.CoroutineActivity

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName

    private var viewModel: MainViewModel? = null


    private lateinit var recyclerView: RecyclerView

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
        )
    }


    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}