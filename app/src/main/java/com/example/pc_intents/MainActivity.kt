package com.example.pc_intents

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pc_intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val extras = result.data?.extras
            val bitmap = extras?.get("data") as Bitmap
            var foto = binding.ivFoto
            foto.setImageBitmap(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWeb.setOnClickListener {
            val portal = Uri.parse(binding.textFieldWeb.editText?.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, portal)
            val activities =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            val isIntentSafe = activities.isEmpty()
            val title = "Elegir Aplicacion"
            val wChooser = Intent.createChooser(intent, title)
            if (isIntentSafe) {
                startActivity(wChooser)
            }
        }

        binding.btnEmail.setOnClickListener {
            val email = Uri.parse("mailto:${binding.textFieldEmail.editText?.text.toString()}")
            val intent = Intent(Intent.ACTION_SENDTO, email)
            startActivity(intent)
        }

        binding.btnLocation.setOnClickListener {
            val location =
                Uri.parse("geo:0.0?q=${binding.textFieldLocation.editText?.text.toString()}");
            val intent = Intent(Intent.ACTION_VIEW, location)
            startActivity(intent)
        }

        binding.btnNumber.setOnClickListener {
            val number = Uri.parse("tel:${binding.textFieldNumber.editText?.text.toString()}")
            val intent = Intent(Intent.ACTION_DIAL,number)
            startActivity(intent)
        }

        binding.btnSweb.setOnClickListener {
            val q = binding.textFieldSweb.editText?.text.toString()
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, q);
            startActivity(intent)
        }

        binding.btnCamera.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(intent)
        }
    }
}