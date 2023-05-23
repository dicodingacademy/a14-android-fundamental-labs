package com.dicoding.picodiploma.myappbar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.myappbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    /*
                    Ketika menu ditekan maka akan memunculkan fragment,
                    fragment di tambahkan ke dalam backstack sehingga ketika ditekan back akan kembali ke MainActivity
                     */
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MenuFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
