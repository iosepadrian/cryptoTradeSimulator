package com.example.myapplication.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.CoinApiAdapter
import com.example.myapplication.data.data.adapters.FavCoinAdapter
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.data.data.model.User
import com.example.myapplication.modelView.FavCoinModelView
import com.example.myapplication.modelView.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_page3.*
import kotlinx.android.synthetic.main.fragment_page3.view.*


@AndroidEntryPoint
class Page3Fragment : Fragment() {
    private var checkString="asc"
    private lateinit var viewOfLayout: View
    private lateinit var adapter:FavCoinAdapter
    private lateinit var viewModel: FavCoinModelView
    private lateinit var userviewModel:UserViewModel
    var navc: NavController?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc= Navigation.findNavController(view)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userviewModel=ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        viewOfLayout = inflater.inflate(R.layout.fragment_page3, container, false)
        viewModel= ViewModelProvider(requireActivity()).get(FavCoinModelView::class.java)

        val recyclerView =  viewOfLayout.favoriterecyclerView


        setFavCoinList(recyclerView)
        handleProfileImage()
        handleChangePassword()
        handleAutoLogin()
        sortList(recyclerView)

        return viewOfLayout
    }





    private fun handleAutoLogin() {
        val key: Int
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        key = prefs.getInt("autologinkey", 0)

        if(key>0){
            viewOfLayout.switchautologin.isChecked=true
        }

        viewOfLayout.switchautologin.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                // when switch button is checked
                val prefs: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(requireContext())

                prefs.edit().putInt("autologinkey", 1).apply()
            }else{
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                prefs.edit().clear().apply()
            }
        }

    }

    private fun handleChangePassword() {
        viewOfLayout.changepassbutton.setOnClickListener {
            changepassbutton.startAnimation(AnimationUtils.loadAnimation(this.activity,R.anim.bounce))
            userviewModel.user.observe(viewLifecycleOwner) { user ->
                if (user.username == viewOfLayout.usernameedittext.text.toString()) {
                    if (viewOfLayout.emailedittext.text.toString() != "") {
                        val user1 = User(
                            user.id,
                            viewOfLayout.emailedittext.text.toString(),
                            user.username,
                            user.uri,
                            user.balance,
                            user.noOfTransactions
                        )
                        userviewModel.insertUser(user1)
                        Toast.makeText(requireContext(), "Password changed", Toast.LENGTH_SHORT)
                            .show()
                    } else Toast.makeText(
                        requireContext(),
                        "Password cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "User doesn't match", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    private fun setFavCoinList(recyclerView: RecyclerView?) {
        adapter= FavCoinAdapter()
        viewModel.favcoin.observe(viewLifecycleOwner) { details ->
            recyclerView.also {
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                    renderPhotosList(details)
                }
            }
        }
        adapter.setOnItemClickListener(object : FavCoinAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page3Fragment_to_deatilFragment,bundle)
            }
        })
    }

    private fun handleProfileImage() {
        userviewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.uri == "") {
                viewOfLayout.userPhoto.setImageResource(R.drawable.user)
            } else {
                viewOfLayout.userPhoto.setImageURI(Uri.parse(user.uri))
            }
        }


        viewOfLayout.uploadimage.setOnClickListener{
            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        viewOfLayout.deleteimage.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    viewOfLayout.userPhoto.setImageResource(R.drawable.user)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }

    private fun sortList(recyclerView:RecyclerView) {
        val ascdscimage=viewOfLayout.ascfavdscimg
        if(checkString=="asc"){
            ascdscimage.setImageResource(R.drawable.asc)
        }
        else {
            ascdscimage.setImageResource(R.drawable.dsc)
        }

        ascdscimage.setOnClickListener {
            if (checkString == "asc") {
                ascdscimage.setImageResource(R.drawable.dsc)
                checkString="dsc"
                sortbyDescendingName(recyclerView)
            }
            else {
                ascdscimage.setImageResource(R.drawable.asc)
                checkString = "asc"
                sortCoinsByName(recyclerView)
            }
        }

    }

    private fun sortCoinsByName(recyclerView: RecyclerView) {
        adapter= FavCoinAdapter()
        viewModel.favcoin.observe(viewLifecycleOwner) { coins ->
            recyclerView.also { it ->
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins.sortedBy { it.name })

            }
        }
        adapter.setOnItemClickListener(object : FavCoinAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page3Fragment_to_deatilFragment,bundle)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderPhotosList(sortedBy: List<Favcoin>) {
        adapter.addData(sortedBy)
        adapter.notifyDataSetChanged()
    }

    private fun sortbyDescendingName(recyclerView: RecyclerView) {
        adapter= FavCoinAdapter()
        viewModel.favcoin.observe(viewLifecycleOwner) { coins ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = adapter
                renderPhotosList(coins.sortedByDescending { it.name })
            }
        }
        adapter.setOnItemClickListener(object : FavCoinAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.v("AdiTag","Item clicked")
                val bundle = Bundle()
                bundle.putString("id", adapter.returnData()[position].id)
                navc?.navigate(R.id.action_page3Fragment_to_deatilFragment,bundle)
            }
        })
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            viewOfLayout.userPhoto.setImageURI(data?.data)
            userviewModel.user.observe(viewLifecycleOwner) { user ->
                val usertoUpdate = User(
                    user.id,
                    user.password,
                    user.username,
                    data?.data.toString(),
                    user.balance,
                    user.noOfTransactions
                )
                userviewModel.insertUser(usertoUpdate)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //Permission code
        private val PERMISSION_CODE = 1001
    }

}