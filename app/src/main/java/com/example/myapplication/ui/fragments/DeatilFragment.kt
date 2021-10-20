package com.example.myapplication.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinDetailsAdapter
import com.example.myapplication.data.data.api.ApiService
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.modelView.CoinDetailsViewModel
import com.example.myapplication.modelView.factories.CoinDetailsFactory
import com.example.myapplication.repository.CoinDetailsRepository
import com.example.myapplication.repository.database.FavCoinDatabase
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_deatil.view.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DeatilFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var factory: CoinDetailsFactory
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var lineChart:LineChart
    private lateinit var lineChart2:LineChart
    private lateinit var lineList:ArrayList<Entry>
    private lateinit var lineList2:ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineDataSet2: LineDataSet
    private lateinit var lineData: LineData
    private lateinit var lineData2: LineData
    private lateinit var recyclerView: RecyclerView
    private lateinit var barChart:BarChart
    private lateinit var barLineList:ArrayList<BarEntry>
    private lateinit var progressbar:ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "NotifyDataSetChanged", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_deatil, container, false)

        /*val api= ApiService()
        val repository= CoinDetailsRepository(api)
        GlobalScope.launch(Dispatchers.Main) {
            val coins = repository.getCoinDetails("01coin")
            Log.v("Aditag", coins.toString())
        }*/
        progressbar=viewOfLayout.coinDetailProgressBar
        progressbar.visibility=View.VISIBLE
        /*Handler().postDelayed({
            progressbar.visibility=View.INVISIBLE
            viewOfLayout.coinRank.visibility=View.VISIBLE
            viewOfLayout.coinimage.visibility=View.VISIBLE
            viewOfLayout.headerId.visibility=View.VISIBLE
            viewOfLayout.favouriteImage.visibility=View.VISIBLE
            viewOfLayout.divider1.visibility=View.VISIBLE
            viewOfLayout.divider2.visibility=View.VISIBLE
            viewOfLayout.aboutValue.visibility=View.VISIBLE
            viewOfLayout.aboutTextView.visibility=View.VISIBLE
            viewOfLayout.websiteValue.visibility=View.VISIBLE
            viewOfLayout.websiteTextValue.visibility=View.VISIBLE
            viewOfLayout.categoriesTextView.visibility=View.VISIBLE
            viewOfLayout.categoriesChipGroup.visibility=View.VISIBLE
            viewOfLayout.pricesTextView.visibility=View.VISIBLE
            viewOfLayout.linechart.visibility=View.VISIBLE
            viewOfLayout.buttonsconstraint.visibility=View.VISIBLE
            viewOfLayout.changechartbutton.visibility=View.VISIBLE
            viewOfLayout.layout.visibility=View.VISIBLE
        },2000)*/

        val id= arguments?.getString("id")
        val api=ApiService()
        val repository=CoinDetailsRepository(api)
        factory = CoinDetailsFactory(repository)
        viewModel= ViewModelProviders.of(this,factory).get(CoinDetailsViewModel::class.java)
        if (id != null) {
            viewModel.getcoinDetails(id)
            viewModel.getcoinTopDetails(id)
            viewModel.getcoinDetailsForOneDay(id)
            viewModel.getcoinDetailsForTwoDays(id)
        }


        recyclerView =  viewOfLayout.detailRecyclerView

        barChart=viewOfLayout.barchart
        setRecyclerViewForOneDay(recyclerView)
        setSwipeToRefrestData1(recyclerView)
        setToolBarBackButton()
        if (id != null) {
            setFavouriteImage(id,viewOfLayout)
        }
        setChipGroups()
        handleChart()
        handleChangeButton()

        return viewOfLayout
    }

    private fun handleChangeButton() {
        val changebutton=viewOfLayout.changechartbutton
        changebutton.setOnClickListener {
            if (barChart.visibility == View.VISIBLE) {
                barChart.visibility = View.INVISIBLE
                lineChart.visibility = View.VISIBLE
                lineChart2.visibility = View.INVISIBLE
            } else {
                if (lineChart.visibility == View.VISIBLE) {
                    barChart.visibility = View.INVISIBLE
                    lineChart.visibility = View.INVISIBLE
                    lineChart2.visibility = View.VISIBLE
                } else {
                    barChart.visibility = View.VISIBLE
                    lineChart.visibility = View.INVISIBLE
                    lineChart2.visibility = View.INVISIBLE
                }
            }
        }
    }


    private fun setBarChartForOneDay(){
        barLineList= ArrayList()
        viewModel.one.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                barLineList.add(BarEntry(i.toFloat(), price[1].take(6).toFloat()))
            }
            val barDataSet=BarDataSet(barLineList,"Dataset")
            val barData=BarData()
            barData.addDataSet(barDataSet)
            barChart.data=barData
            barChart.invalidate()
            barDataSet.valueTextSize=0f
            barChart.axisLeft.setDrawGridLines(false)
            val xAxis: XAxis = barChart.xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)

            barChart.axisRight.isEnabled = false

            barChart.legend.isEnabled = false

            barChart.description.isEnabled = false

            barChart.animateX(1000, Easing.EaseInSine)
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.valueFormatter = MyAxisFormatter1()
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = +90f
        })
        setRecyclerViewForOneDay(recyclerView)
    }
    private fun setBarChartForSevenDays(){
        barLineList= ArrayList()
        viewModel.details.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                barLineList.add(BarEntry(i.toFloat(), price[1].take(6).toFloat()))
            }
            val barDataSet=BarDataSet(barLineList,"Dataset")
            val barData=BarData()
            barData.addDataSet(barDataSet)
            barChart.data=barData
            barChart.invalidate()
            barDataSet.valueTextSize=0f
            barChart.axisLeft.setDrawGridLines(false)
            val xAxis: XAxis = barChart.xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)

            barChart.axisRight.isEnabled = false

            barChart.legend.isEnabled = false

            barChart.description.isEnabled = false

            barChart.animateX(1000, Easing.EaseInSine)
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.valueFormatter = MyAxisFormatter7()
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = +90f
        })
        setRecyclerViewFor7Days(recyclerView)
    }
    private fun setBarChartForTwoDays(){
        barLineList= ArrayList()
        viewModel.two.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                barLineList.add(BarEntry(i.toFloat(), price[1].take(6).toFloat()))
            }
            val barDataSet=BarDataSet(barLineList,"Dataset")
            val barData=BarData()
            barData.addDataSet(barDataSet)
            barChart.data=barData
            barChart.invalidate()
            barDataSet.valueTextSize=0f
            barChart.axisLeft.setDrawGridLines(false)
            val xAxis: XAxis = barChart.xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)

            barChart.axisRight.isEnabled = false

            barChart.legend.isEnabled = false

            barChart.description.isEnabled = false

            barChart.animateX(1000, Easing.EaseInSine)
            xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
            xAxis.valueFormatter = MyAxisFormatter2()
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = +90f
        })
        setRecyclerViewForTwoDays(recyclerView)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleChart() {
        lineChart=viewOfLayout.linechart
        lineChart2=viewOfLayout.linechart2
        initLineChart1()
        setLineChartForOneDay()
        initLineChart21()
        setLineChar2tForOneDay()
        setBarChartForOneDay()

        val button1=viewOfLayout.onedaybutton
        val button7=viewOfLayout.sevendaysbutton
        val button2=viewOfLayout.twodaysbutton
        button7.setOnClickListener{
            initLineChart7()
            initLineChart27()
            setLineChartForSevenDays()
            setBarChartForSevenDays()
            setLineChart2ForSevenDays()

            //setDataToLineChart()

            button7.setBackgroundResource(R.drawable.button_white)
            button1.setBackgroundResource(R.drawable.button_grey)
            button2.setBackgroundResource(R.drawable.button_grey)
            button7.elevation=2f
            button1.elevation=0f
            button2.elevation=0f
            setSwipeToRefrestData7(recyclerView)
        }
        button1.setOnClickListener{
            button7.setBackgroundResource(R.drawable.button_grey)
            button1.setBackgroundResource(R.drawable.button_white)
            button2.setBackgroundResource(R.drawable.button_grey)
            initLineChart1()
            initLineChart21()
            setLineChartForOneDay()
            setBarChartForOneDay()
            setLineChar2tForOneDay()

            setSwipeToRefrestData1(recyclerView)
            button7.elevation=0f
            button1.elevation=2f
            button2.elevation=0f
        }
        button2.setOnClickListener{
            button7.setBackgroundResource(R.drawable.button_grey)
            button1.setBackgroundResource(R.drawable.button_grey)
            button2.setBackgroundResource(R.drawable.button_white)
            initLineChart2()
            initLineChart22()
            setLineChartForTwoDays()
            setBarChartForTwoDays()
            setLineChart2ForTwoDays()
            setSwipeToRefrestData2(recyclerView)
            button7.elevation=0f
            button2.elevation=2f
            button1.elevation=0f
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChartForSevenDays() {
        lineList= ArrayList()
        viewModel.details.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet= LineDataSet(lineList,"")
            lineData= LineData(lineDataSet)
            lineChart.data=lineData
            lineDataSet.setDrawFilled(true)
            lineDataSet.valueTextSize=0f
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChart2ForSevenDays() {
        lineList2= ArrayList()
        viewModel.details.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList2.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet2= LineDataSet(lineList2,"")
            lineData2= LineData(lineDataSet2)
            lineChart2.data=lineData2
            lineDataSet2.valueTextSize=0f
            lineDataSet2.setDrawCircles(false)
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChartForTwoDays() {
        lineList= ArrayList()
        viewModel.two.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet= LineDataSet(lineList,"")
            lineData= LineData(lineDataSet)
            lineChart.data=lineData
            lineDataSet.setDrawFilled(true)
            lineDataSet.valueTextSize=0f
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChart2ForTwoDays() {
        lineList2= ArrayList()
        viewModel.two.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList2.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet2= LineDataSet(lineList2,"")
            lineData2= LineData(lineDataSet2)
            lineChart2.data=lineData2
            lineDataSet2.valueTextSize=0f
            lineDataSet2.setDrawCircles(false)
        })

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChartForOneDay() {
        lineList= ArrayList()
        viewModel.one.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet= LineDataSet(lineList,"")
            lineData= LineData(lineDataSet)
            lineChart.data=lineData
            lineDataSet.setDrawFilled(true)
            lineDataSet.valueTextSize=0f
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChar2tForOneDay() {
        lineList2= ArrayList()
        viewModel.one.observe(viewLifecycleOwner, { details ->
            var   i=-1
            for(price in details.prices){
                i += 1
                lineList2.add(Entry(i.toFloat(), price[1].take(6).toFloat()))

            }
            lineDataSet2= LineDataSet(lineList2,"")
            lineData2= LineData(lineDataSet2)
            lineChart2.data=lineData2
            lineDataSet2.valueTextSize=0f
            lineDataSet2.setDrawCircles(false)
        })
    }


    private fun initLineChart7() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart.axisRight.isEnabled = false

        lineChart.legend.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter7()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }
    private fun initLineChart27() {
        lineChart2.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart2.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart2.axisRight.isEnabled = false

        lineChart2.legend.isEnabled = false

        lineChart2.description.isEnabled = false

        lineChart2.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter7()
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }

    private fun initLineChart2() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart.axisRight.isEnabled = false

        lineChart.legend.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter2()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }
    private fun initLineChart22() {
        lineChart2.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart2.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart2.axisRight.isEnabled = false

        lineChart2.legend.isEnabled = false

        lineChart2.description.isEnabled = false

        lineChart2.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter2()
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }

    private fun initLineChart1() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart.axisRight.isEnabled = false

        lineChart.legend.isEnabled = false

        lineChart.description.isEnabled = false

        lineChart.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter1()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }



    private fun initLineChart21() {
        lineChart2.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart2.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        lineChart2.axisRight.isEnabled = false

        lineChart2.legend.isEnabled = false

        lineChart2.description.isEnabled = false

        lineChart2.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter1()
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }


    inner class MyAxisFormatter7 : IndexAxisValueFormatter() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            var str=""


            viewModel.details.observe(viewLifecycleOwner,{details->

                if(value.toInt()<details.prices.size) {
                    val date = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(details.prices[value.toInt()][0].toLong()),
                        ZoneId.systemDefault()
                    )
                    str = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                }
            })
            return str
        }

    }
    inner class MyAxisFormatter2 : IndexAxisValueFormatter() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            var str=""


            viewModel.two.observe(viewLifecycleOwner,{details->

                if(value.toInt()<details.prices.size) {
                    val date = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(details.prices[value.toInt()][0].toLong()),
                        ZoneId.systemDefault()
                    )
                    str = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                }
            })
            return str
        }

    }

    inner class MyAxisFormatter1 : IndexAxisValueFormatter() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            var str=""
            viewModel.one.observe(viewLifecycleOwner,{details->

                if(value.toInt()<details.prices.size) {
                    val date = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(details.prices[value.toInt()][0].toLong()),
                        ZoneId.systemDefault()
                    )
                    str = date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
                }
            })
            return str
        }

    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun setChipGroups() {
        val chipGroup=viewOfLayout.categoriesChipGroup
        viewModel.topDetails.observe(viewLifecycleOwner, { topDetails->
            viewOfLayout.coinRank.text="#"+ topDetails.coingecko_rank
            viewOfLayout.headerId.text=topDetails.name
            Glide.with(viewOfLayout.coinimage.context).load(topDetails.image.small).into(viewOfLayout.coinimage)
            if(topDetails.description.en==""){
                viewOfLayout.aboutValue.text="There is nothing written about this site"
            }
            else {
                viewOfLayout.aboutValue.text = topDetails.description.en
            }
            for(string in topDetails.categories){
                val chip = Chip(activity)
                chip.text = string
                chip.setChipBackgroundColorResource(R.color.chip_backgroud)
                chip.isCloseIconVisible = true
                chip.setTextColor(R.color.chip_text)
                chip.setTextAppearance(R.style.ChipTextAppearance)
                chip.elevation=2F

                chip.isCloseIconVisible = false
                chipGroup.addView(chip)
            }
            viewOfLayout.websiteValue.text= topDetails.links.homepage[0]
            progressbar.visibility=View.INVISIBLE
            viewOfLayout.coinRank.visibility=View.VISIBLE
            viewOfLayout.coinimage.visibility=View.VISIBLE
            viewOfLayout.headerId.visibility=View.VISIBLE
            viewOfLayout.favouriteImage.visibility=View.VISIBLE
            viewOfLayout.divider1.visibility=View.VISIBLE
            viewOfLayout.divider2.visibility=View.VISIBLE
            viewOfLayout.aboutValue.visibility=View.VISIBLE
            viewOfLayout.aboutTextView.visibility=View.VISIBLE
            viewOfLayout.websiteValue.visibility=View.VISIBLE
            viewOfLayout.websiteTextValue.visibility=View.VISIBLE
            viewOfLayout.categoriesTextView.visibility=View.VISIBLE
            viewOfLayout.categoriesChipGroup.visibility=View.VISIBLE
            viewOfLayout.pricesTextView.visibility=View.VISIBLE
            viewOfLayout.linechart.visibility=View.VISIBLE
            viewOfLayout.buttonsconstraint.visibility=View.VISIBLE
            viewOfLayout.changechartbutton.visibility=View.VISIBLE
            viewOfLayout.layout.visibility=View.VISIBLE

        })
    }


    private fun setRecyclerViewFor7Days(recyclerView: RecyclerView?) {
        viewModel.details.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= CoinDetailsAdapter(details)
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
        })
    }
    private fun setRecyclerViewForOneDay(recyclerView: RecyclerView?) {
        viewModel.one.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= CoinDetailsAdapter(details)
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
        })
    }
    private fun setRecyclerViewForTwoDays(recyclerView: RecyclerView?) {
        viewModel.two.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= CoinDetailsAdapter(details)
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setSwipeToRefrestData7(recyclerView:RecyclerView){
        swipeRefreshLayout=viewOfLayout.swiperefresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.details.observe(viewLifecycleOwner, { details ->
                recyclerView.also {
                    val adapter = CoinDetailsAdapter(details)
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setSwipeToRefrestData2(recyclerView:RecyclerView){
        swipeRefreshLayout=viewOfLayout.swiperefresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.two.observe(viewLifecycleOwner, { details ->
                recyclerView.also {
                    val adapter = CoinDetailsAdapter(details)
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setSwipeToRefrestData1(recyclerView:RecyclerView){
        swipeRefreshLayout=viewOfLayout.swiperefresh
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.one.observe(viewLifecycleOwner, { details ->
                recyclerView.also {
                    val adapter = CoinDetailsAdapter(details)
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setToolBarBackButton(){
        val toolbar=viewOfLayout.detailToolbar
        toolbar.    navigationIcon?.setColorFilter(Color.parseColor("#e8a48f"), PorterDuff.Mode.SRC_IN)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()

        }

    }

    private fun setFavouriteImage(id:String,view:View){
        val favImageView=viewOfLayout.favouriteImage
        val db=FavCoinDatabase.getDatabase(this.requireContext())
        favImageView.tag = R.drawable.ic_emplyheart
        favImageView.setColorFilter(Color.parseColor("#444444"))
        for(coin in db.favCoinDao().loadAllNoSync()){
            if (coin.id==id){
                favImageView.setImageResource(R.drawable.ic_filledheart)
                favImageView.tag = R.drawable.ic_filledheart
                favImageView.setColorFilter(Color.parseColor("#FF0000"))
            }
        }

        favImageView.setOnClickListener{
            if(favImageView.tag ==R.drawable.ic_emplyheart)
            {
                favImageView.setImageResource(R.drawable.ic_filledheart)
                favImageView.tag = R.drawable.ic_filledheart
                favImageView.setColorFilter(Color.parseColor("#FF0000"))
                val favcoin= Favcoin(id,view.headerId.text.toString())
                db.favCoinDao().insert(favcoin)
            }
            else
            {
                favImageView.setImageResource(R.drawable.ic_emplyheart)
                favImageView.tag = R.drawable.ic_emplyheart
                favImageView.setColorFilter(Color.parseColor("#444444"))
                val favcoin= Favcoin(id,view.headerId.text.toString())
                db.favCoinDao().delete(favcoin)
            }

        }
    }


}