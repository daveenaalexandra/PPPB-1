package com.example.presensiapp // GANTI DENGAN NAMA PAKET PROYEK ANDA

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // Deklarasi untuk semua komponen UI
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var spinnerStatus: Spinner
    private lateinit var editTextReason: EditText
    private lateinit var timePickerText: TextView

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi semua view dari layout
        monthSpinner = findViewById(R.id.spinner_month)
        yearSpinner = findViewById(R.id.spinner_year)
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        editTextReason = findViewById(R.id.editTextReason)
        timePickerText = findViewById(R.id.timePickerText)

        // Memanggil semua fungsi setup
        setupSpinners()
        setupCalendar()
        setupTimePicker()
        setupStatusSpinner()
    }

    private fun setupSpinners() {
        // Setup Spinner Bulan
        val months = SimpleDateFormat("MMMM", Locale.getDefault()).calendar.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months.keys.toTypedArray())
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter
        monthSpinner.setSelection(calendar.get(Calendar.MONTH))

        // Setup Spinner Tahun
        val currentYear = calendar.get(Calendar.YEAR)
        val years = (currentYear - 5..currentYear + 5).map { it.toString() }
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter
        yearSpinner.setSelection(years.indexOf(currentYear.toString()))

        // Listener untuk memperbarui kalender saat bulan/tahun diubah
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateCalendar()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        monthSpinner.onItemSelectedListener = spinnerListener
        yearSpinner.onItemSelectedListener = spinnerListener
    }

    private fun setupCalendar() {
        calendarRecyclerView.layoutManager = GridLayoutManager(this, 7)
        updateCalendar()
    }

    private fun updateCalendar() {
        val selectedMonth = monthSpinner.selectedItemPosition
        val selectedYear = yearSpinner.selectedItem.toString().toInt()

        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.YEAR, selectedYear)

        val days = generateDaysForMonth(calendar)
        calendarAdapter = CalendarAdapter(days)
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun generateDaysForMonth(cal: Calendar): List<Date?> {
        val days = mutableListOf<Date?>()
        val tempCal = cal.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)

        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1 // Sunday = 0
        val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until firstDayOfWeek) {
            days.add(null)
        }

        for (i in 1..daysInMonth) {
            tempCal.set(Calendar.DAY_OF_MONTH, i)
            days.add(tempCal.time)
        }
        return days
    }

    private fun setupTimePicker() {
        timePickerText.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                // Format waktu menjadi "12 : 00 AM/PM"
                timePickerText.text = SimpleDateFormat("hh : mm a", Locale.getDefault()).format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }
    }

    private fun setupStatusSpinner() {
        val statusOptions = arrayOf("Hadir Tepat Waktu", "Sakit", "Izin", "Terlambat")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()

                if (selectedItem != "Hadir Tepat Waktu") {
                    editTextReason.visibility = View.VISIBLE
                } else {
                    editTextReason.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                editTextReason.visibility = View.GONE
            }
        }
    }
}

// --- Calendar Adapter ---
class CalendarAdapter(private val days: List<Date?>) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    private var selectedPosition = -1
    private val today = Calendar.getInstance()

    init {
        days.forEachIndexed { index, date ->
            if (date != null) {
                val cal = Calendar.getInstance().apply { time = date }
                if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                    selectedPosition = index
                    return@forEachIndexed
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        (view as TextView).apply {
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            layoutParams.height = 120 // Atur tinggi setiap sel tanggal
        }
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount(): Int = days.size

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayText: TextView = itemView as TextView

        init {
            itemView.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION && days[adapterPosition] != null) {
                    val previousSelected = selectedPosition
                    selectedPosition = adapterPosition
                    if (previousSelected != -1) {
                        notifyItemChanged(previousSelected)
                    }
                    notifyItemChanged(selectedPosition)
                }
            }
        }

        fun bind(date: Date?, position: Int) {
            if (date != null) {
                dayText.text = SimpleDateFormat("d", Locale.getDefault()).format(date)
                dayText.visibility = View.VISIBLE

                if (position == selectedPosition) {
                    dayText.setBackgroundResource(R.drawable.selected_day_background)
                    dayText.setTextColor(Color.WHITE)
                } else {
                    dayText.background = null
                    dayText.setTextColor(Color.BLACK)
                }

            } else {
                dayText.text = ""
                dayText.visibility = View.INVISIBLE
                dayText.background = null
            }
        }
    }
}

