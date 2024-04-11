package com.example.puslewiseapp

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.puslewiseapp.databinding.ActivityAddPulsewiseItemsBinding
import com.example.puslewiseapp.feature_pulsewise.data.local.dto.LocalPulsewiseItem
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Suppress("DEPRECATION")
class AddPulsewiseItems : AppCompatActivity() {

    private lateinit var binding: ActivityAddPulsewiseItemsBinding

    private lateinit var pulsewiseItem: LocalPulsewiseItem
    private lateinit var old_pulsewiseItem: LocalPulsewiseItem
    private val calendar = Calendar.getInstance()
    var isUpdate = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPulsewiseItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystolicNumberPicker()
        setupDiastolicNumberPicker()
        setupPulseNumberPicker()
        binding.datepickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    binding.datepickerButton.text = formattedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.timePickerButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                binding.timePickerButton.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true)
                .show()
        }

        binding.saveItemButton.setOnClickListener {
            val systolic = binding.systolicNumberPicker.value
            val diastolic = binding.diastolicNumberPicker.value
            val pulse = binding.pulseNumberPicker.value

            val date = binding.datepickerButton.text

            val time = binding.timePickerButton.text

                if (isUpdate) {
                    pulsewiseItem = LocalPulsewiseItem(
                        systolic, diastolic, pulse ,date.toString(), time.toString(), old_pulsewiseItem.id
                    )
                } else{
                    pulsewiseItem = LocalPulsewiseItem(
                        systolic, diastolic, pulse, date.toString(), time.toString(), null
                    )
                }
                val intent = Intent()
                intent.putExtra("pulsewiseItem", pulsewiseItem)
                setResult(Activity.RESULT_OK, intent)
                finish()
        }
        binding.backNewRecordButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun setupSystolicNumberPicker() {
        val numberPicker = binding.systolicNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = 200
        numberPicker.value = 90
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.addSystolicTv.setText(String.format("%s" , newVal))
        }
    }
    private fun setupDiastolicNumberPicker() {
        val numberPicker = binding.diastolicNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = 200
        numberPicker.value = 90
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.addDiastolicTv.setText(String.format("%s" , newVal))
        }
    }
    private fun setupPulseNumberPicker() {
        val numberPicker = binding.pulseNumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = 200
        numberPicker.value = 90
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.addPulseTv.setText(String.format("%s" , newVal))
        }
    }

}