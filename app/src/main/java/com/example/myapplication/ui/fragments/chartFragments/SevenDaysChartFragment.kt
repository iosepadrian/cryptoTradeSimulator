package com.example.myapplication.ui.fragments.chartFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.modelView.factories.CoinDetailsFactory
import com.example.myapplication.repository.CoinDetailsRepository
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_deatil.view.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class SevenDaysChartFragment : Fragment() {


    private lateinit var viewOfLayout: View
    private lateinit var lineChart: LineChart
    private lateinit var lineList:ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData

    private lateinit var factory: CoinDetailsFactory
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var id:String
    private lateinit var page:String



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        id = getArguments()?.getString("someId").toString();
        page = getArguments()?.getString("someTPage").toString()


        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_deatil, container, false)
        val api= ApiService()
        val repository= CoinDetailsRepository(api)
        factory = CoinDetailsFactory(repository)
        viewModel= ViewModelProviders.of(this,factory).get(CoinDetailsViewModel::class.java)
        if (id != null) {
            viewModel.getcoinDetails(id)
            viewModel.getcoinTopDetails(id)
        }

        lineChart=viewOfLayout.linechart
        initLineChart()
        //setDataToLineChart()

        setLineChart()


        return viewOfLayout
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChart() {
        lineList= ArrayList()
        viewModel.details.observe(viewLifecycleOwner, { details ->
            var   i=0
            for(price in details.prices){
                val date = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(price[0].toLong()),
                    ZoneId.systemDefault()
                )
                val data=date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                val current= LocalDateTime.now()
                val currentDate=current.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                Log.v("AdiTag",data)
                Log.v("AdiTag",currentDate.toString())
                lineList.add(Entry(i.toFloat(),price[1].take(6).toFloat()))
                i += 1
                Log.v("AdiTag",i.toString())
            }
            lineDataSet= LineDataSet(lineList,"")
            lineData= LineData(lineDataSet)
            lineChart.data=lineData
            lineDataSet.setDrawFilled(true)
            lineDataSet.valueTextSize=0f
        })

    }

    private fun initLineChart() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart.axisRight.isEnabled = false

        lineChart.legend.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }
    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return ""
        }

    }

    private fun newInstances(page:String?,id: String?): SevenDaysChartFragment? {
        val fragmentFirst = SevenDaysChartFragment()
        val args = Bundle()
        args.putString("someId", id)
        args.putString("somePage", page)
        fragmentFirst.setArguments(args)
        return fragmentFirst
    }
}