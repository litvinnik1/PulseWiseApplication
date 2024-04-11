package com.example.puslewiseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.puslewiseapp.databinding.ActivityHistoryBinding
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem
import com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main.PulsewiseHistoryLitsAdapter
import com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main.PulsewiseListAdapter
import com.example.puslewiseapp.feature_pulsewise.presentation.pulsewise_main.PulsewiseMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HistoryActivity : AppCompatActivity(), PulsewiseHistoryLitsAdapter.PulsewiseClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityHistoryBinding

    private val viewModel: PulsewiseMainViewModel by viewModels()
    lateinit var adapter: PulsewiseHistoryLitsAdapter
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
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        viewModel.allPulsewiseItems.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
                adapter.filterList()
            }
        }

        binding.backHistoryButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initUI() {
        binding.pulsewiseHistoryRecyclerView.setHasFixedSize(true)
        binding.pulsewiseHistoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = PulsewiseHistoryLitsAdapter(this, this)
        binding.pulsewiseHistoryRecyclerView.adapter = adapter

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



    }
    override fun onItemClicked(pulsewiseItem: LocalPulsewiseItem) {
        val intent = Intent(this@HistoryActivity, AddPulsewiseItems::class.java)
        intent.putExtra("current_pomodoro", pulsewiseItem)
        updatePulsewiseItem.launch(intent)
    }

    override fun onLongItemClicked(pulsewiseItem: LocalPulsewiseItem, cardView: CardView) {
        selectedPulsewiseItem = pulsewiseItem
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@HistoryActivity)
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