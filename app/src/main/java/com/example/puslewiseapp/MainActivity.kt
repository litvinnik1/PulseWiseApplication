package com.example.puslewiseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.puslewiseapp.databinding.ActivityMainBinding
import com.example.puslewiseapp.feature_pulsewise.data.local.PulsewiseDatabase
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem
import com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main.PulsewiseListAdapter
import com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main.PulsewiseMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PulsewiseListAdapter.PulsewiseClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: PulsewiseDatabase
    private val viewModel: PulsewiseMainViewModel by viewModels()
    lateinit var adapter: PulsewiseListAdapter
    lateinit var selectedPulsewiseItem: LocalPulsewiseItem

    private val updatePulsewiseItem = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val pulsewiseItem = result.data?.getSerializableExtra("note") as? LocalPulsewiseItem
            if (pulsewiseItem != null) {
                viewModel.addPulsewiseItem(pulsewiseItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing the UI
        initUI()
        viewModel.allPulsewiseItems.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

    }

    private fun initUI() {
        binding.pulsewiseRecyclerView.setHasFixedSize(true)
        binding.pulsewiseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = PulsewiseListAdapter(this, this)
        binding.pulsewiseRecyclerView.adapter = adapter

        val getContent = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val pulsewiseItem = result.data?.getSerializableExtra("pulsewiseItem") as? LocalPulsewiseItem
                if(pulsewiseItem != null){
                    viewModel.addPulsewiseItem(pulsewiseItem)
                }
            }
        }

        binding.addNewPulsewiseItemButton.setOnClickListener {
            val intent = Intent(this, AddPulsewiseItems::class.java)
            getContent.launch(intent)
        }

    }

    override fun onItemClicked(pulsewiseItem: LocalPulsewiseItem) {
        val intent = Intent(this@MainActivity, AddPulsewiseItems::class.java)
        intent.putExtra("current_pomodoro", pulsewiseItem)
        updatePulsewiseItem.launch(intent)
    }

    override fun onLongItemClicked(pulsewiseItem: LocalPulsewiseItem, cardView: CardView) {
        selectedPulsewiseItem = pulsewiseItem
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_pomodoro){
            viewModel.deletePulsewiseItem(selectedPulsewiseItem)
            return true
        }
        return false
    }
}