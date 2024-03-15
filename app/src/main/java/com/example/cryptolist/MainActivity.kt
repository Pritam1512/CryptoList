package com.example.cryptolist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptolist.api.CryptoService
import com.example.cryptolist.api.RetrofitHelper
import com.example.cryptolist.models.Cryptocurrency
import com.example.cryptolist.repository.CryptoRepository
import com.example.cryptolist.viewmodels.MainViewModel
import com.example.cryptolist.viewmodels.MainViewModelFactory
import java.security.AccessController.getContext
import javax.security.auth.login.LoginException


class MainActivity : AppCompatActivity(),View.OnClickListener{

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var myList: ArrayList<Cryptocurrency>
    private lateinit var searchView: SearchView
    private lateinit var cryptoAdapter: MyAdapter

    private var isActiveCoinButtonActive  = false
    private var isInActiveCoinButtonActive  = false
    private var isOnlyTokenButtonActive  = false
    private var isOnlyCoinButtonActive  = false
    private var isNewCoinButtonActive  = false



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("CL_MainActivity","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val cryptoService = RetrofitHelper.getInstance().create(CryptoService::class.java)
        val repository = CryptoRepository(cryptoService)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository))[MainViewModel::class.java]
        mainViewModel.crypto.observe(this, Observer {
            myList = it.cryptocurrencies
            setMyAdapter(myList)
        })

        // handle filer logic here

        var active_button = findViewById<Button>(R.id.button_active_coin)
        var inactive_button = findViewById<Button>(R.id.button_inactive_coin)
        var only_token_button = findViewById<Button>(R.id.button_only_token)
        var only_coin_button = findViewById<Button>(R.id.button_only_coin)
        var new_coin_button = findViewById<Button>(R.id.button_new_coin)

        active_button.setOnClickListener(this)
        inactive_button.setOnClickListener(this)
        only_coin_button.setOnClickListener(this)
        only_token_button.setOnClickListener(this)
        new_coin_button.setOnClickListener(this)



    }

    private fun setMyAdapter(myList: ArrayList<Cryptocurrency>) {
        for(i in myList){
            Log.i("CL_MainActivity", "onCreate: " + i.name)
        }
        newRecyclerView = findViewById(R.id.cryptoList)
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.adapter = MyAdapter(myList)
        newRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
        getTextFromSearchView(searchView,myList)
    }

    private fun getTextFromSearchView(searchView: SearchView,myList: ArrayList<Cryptocurrency>) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search operation with the query
                Log.i("CL_MainActivity", "onQueryTextSubmit: $query")
                filterList(query,myList);
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Update search results based on the new query text
                Log.i("CL_MainActivity", "onQueryTextSubmit: $newText")
                newRecyclerView.adapter = MyAdapter(myList)
                return true
            }
        })
    }

    private fun filterList(query: String,myList: ArrayList<Cryptocurrency>) {

        var filterList = ArrayList<Cryptocurrency>()

        Log.i("MainActivity", "filterList: $myList")
        for(i in myList){
            if(i.name.contains(query,ignoreCase = true) || i.symbol.contains(query,ignoreCase = true)){
                filterList.add(i)
            }
        }
        Log.i("MainActivity", "newFilterList: $filterList")
        if(filterList.size == 0){
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            newRecyclerView.adapter = MyAdapter(myList)
        }else{
            newRecyclerView.adapter = MyAdapter(filterList)
        }

    }

    override fun onClick(v: View) {

        var active_button = findViewById<Button>(R.id.button_active_coin)
        var inactive_button = findViewById<Button>(R.id.button_inactive_coin)
        var only_token_button = findViewById<Button>(R.id.button_only_token)
        var only_coin_button = findViewById<Button>(R.id.button_only_coin)
        var new_coin_button = findViewById<Button>(R.id.button_new_coin)

        when(v.id){
            R.id.button_active_coin->{
                Log.i("MainActivity", "onClick:  active button clicked $isActiveCoinButtonActive")
                isActiveCoinButtonActive = !isActiveCoinButtonActive
                if(isActiveCoinButtonActive){
                    active_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_clicked))
                }else{
                    active_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_idle))
                }
                filterDataOnButtonClicked()
            }
            R.id.button_inactive_coin->{
                Log.i("MainActivity", "onClick:  inactive button clicked $isInActiveCoinButtonActive")
                isInActiveCoinButtonActive = !isInActiveCoinButtonActive
                if(isInActiveCoinButtonActive){
                    inactive_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_clicked))
                }else{
                    inactive_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_idle))
                }
                filterDataOnButtonClicked()
            }
            R.id.button_only_token->{
                Log.i("MainActivity", "onClick:  only token button clicked $isOnlyTokenButtonActive")
                isOnlyTokenButtonActive = !isOnlyTokenButtonActive
                if(isOnlyTokenButtonActive){
                    only_token_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_clicked))
                }else{
                    only_token_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_idle))
                }
                filterDataOnButtonClicked()
            }
            R.id.button_only_coin->{
                Log.i("MainActivity", "onClick:  only coins button clicked $isOnlyCoinButtonActive")
                isOnlyCoinButtonActive = !isOnlyCoinButtonActive
                if(isOnlyCoinButtonActive){
                    only_coin_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_clicked))
                }else{
                    only_coin_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_idle))
                }
                filterDataOnButtonClicked()
            }
            R.id.button_new_coin->{
                Log.i("MainActivity", "onClick: new coins button clicked $isNewCoinButtonActive")
                isNewCoinButtonActive = !isNewCoinButtonActive
                if(isNewCoinButtonActive){
                    new_coin_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_clicked))
                }else{
                    new_coin_button.setBackgroundColor(active_button.context.resources.getColor(R.color.button_idle))
                }
                filterDataOnButtonClicked()
            }

        }
    }

    private fun filterDataOnButtonClicked() {
        // OR LOGIC ON MYLIST
        var filterListOnButtonClick = ArrayList<Cryptocurrency>()
        Log.i("MainActivity", "filterDataOnButtonClicked: globalList $myList")
        Log.i("MainActivity", "filterDataOnButtonClicked: localList $filterListOnButtonClick")
        if(myList.size == 0){
            return
        }

        for(i in myList){
            if(isActiveCoinButtonActive){
                if(i.is_active){
                    filterListOnButtonClick.add(i)
                }
            }
            if(isInActiveCoinButtonActive){
                if(!i.is_active){
                    filterListOnButtonClick.add(i)
                }
            }
            if(isOnlyTokenButtonActive){
                if(i.type == "token"){
                    filterListOnButtonClick.add(i)
                }
            }
            if(isOnlyCoinButtonActive){
                if(i.type == "coin"){
                    filterListOnButtonClick.add(i)
                }
            }
            if(isNewCoinButtonActive){
                if(i.is_new){
                    filterListOnButtonClick.add(i)
                }
            }
        }
        Log.i("MainActivity", "filterDataOnButtonClicked: localList $filterListOnButtonClick")
        if(filterListOnButtonClick.size == 0){
            newRecyclerView.adapter = MyAdapter(myList)
        }else{
            newRecyclerView.adapter = MyAdapter(filterListOnButtonClick)
        }
    }
}