package com.example.myapplication.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
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
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.data.adapters.FavCoinAdapter
import com.example.myapplication.data.data.model.Favcoin
import com.example.myapplication.data.data.model.User
import com.example.myapplication.modelView.FavCoinModelView
import com.example.myapplication.modelView.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_page3.*
import kotlinx.android.synthetic.main.fragment_page3.view.*
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.myapplication.ui.activities.UserActivity


@AndroidEntryPoint
class Page3Fragment : Fragment() {

    private lateinit var viewOfLayout: View
    private val activityViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewOfLayout = inflater.inflate(R.layout.fragment_page3, container, false)
        val model= ViewModelProvider(this).get(FavCoinModelView::class.java)
        val favc1=Favcoin("01coin","01coin")
        val favc2=Favcoin("swissborg","SwissBorg")
        //model.insertFavCoin(favc1)
        val recyclerView =  viewOfLayout.favoriterecyclerView

        model.favcoin.observe(viewLifecycleOwner, { details ->
            recyclerView.also {
                val adapter= FavCoinAdapter(details)
                if (it != null) {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    it.setHasFixedSize(true)
                    it.adapter = adapter
                }
            }
        })

        model.favcoin.observe(viewLifecycleOwner, { coins ->
            Log.v("AdiTag",coins.toString())
        })


        activityViewModel.user.observe(viewLifecycleOwner, { user ->
            if(user.uri==""){
                viewOfLayout.userPhoto.setImageResource(R.drawable.user)
            }
            else{
                viewOfLayout.userPhoto.setImageURI(Uri.parse(user.uri))
            }
        })


        viewOfLayout.uploadimage.setOnClickListener{
            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        viewOfLayout.deleteimage.setOnClickListener {

            viewOfLayout.userPhoto.setImageResource(R.drawable.user)
        }

        viewOfLayout.changepassbutton.setOnClickListener {
            changepassbutton.startAnimation(AnimationUtils.loadAnimation(this.activity,R.anim.bounce))
            activityViewModel.user.observe(viewLifecycleOwner, { user ->
                if(user.username==viewOfLayout.usernameedittext.text.toString()){
                    if(viewOfLayout.emailedittext.text.toString()!="")
                    {
                        val user1=User(user.id,viewOfLayout.emailedittext.text.toString(),user.username,user.uri)
                        activityViewModel.insertUser(user1)
                        Toast.makeText(requireContext(),"Password changed",Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(requireContext(),"Password cannot be empty",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"User doesn't match",Toast.LENGTH_SHORT).show()
                }
            })

        }
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

                prefs.edit().putInt("autologinkey", 1).commit()
            }else{
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                prefs.edit().clear().commit();
            }
        }


        return viewOfLayout
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            viewOfLayout.userPhoto.setImageURI(data?.data)


            activityViewModel.user.observe(viewLifecycleOwner, { user ->
                val usertoUpdate=User(user.id,user.password,user.username,data?.data.toString())
                activityViewModel.insertUser(usertoUpdate)
            })
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
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
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

}