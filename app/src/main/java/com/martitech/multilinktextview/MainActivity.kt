package com.martitech.multilinktextview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martitech.multilinktextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.multiLinkTextView.addLinkClickListener(object :OnLinkClickListener{
            override fun onLinkClick(view: View, linkPosition: Int) {
                when(linkPosition){
                    0-> showToast("Facebook")
                    1-> showToast("Twitter")
                    2-> showToast("Linkedin")
                }
            }

        })
    }

    private fun showToast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }
}