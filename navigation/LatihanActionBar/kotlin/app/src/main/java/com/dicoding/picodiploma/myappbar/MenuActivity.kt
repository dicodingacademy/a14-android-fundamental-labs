package com.dicoding.picodiploma.myappbar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.myappbar.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu1 -> {
                        true
                    }
                    R.id.menu2 -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }
}

