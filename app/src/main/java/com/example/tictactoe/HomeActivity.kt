package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.example.tictactoe.databinding.ActivityHomeBinding
import com.example.tictactoe.singletons.SessionConf

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var launcher:ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}
        setContentView(binding.root)
        binding.gameMode.setOnClickListener(){onChangeGamemode()}
        binding.startCombatBtn.setOnClickListener(){onStartBtn()}
        binding.startingSideLabel.setOnClickListener(){onChangeStartingSide()}
        binding.PlayerOneNameField.addTextChangedListener(){updateStartingSideName()}
        binding.PlayerTwoNameField.addTextChangedListener(){updateStartingSideName()}
    }

    fun onStartBtn(){
        if (SessionConf.isPvC){
            SessionConf.playerOneName = binding.PlayerOneNameField.text.toString()
            SessionConf.playerTwoName = "Computer"
        }else{
            SessionConf.playerOneName = binding.PlayerOneNameField.text.toString()
            SessionConf.playerTwoName = binding.PlayerTwoNameField.text.toString()
        }
        launcher?.launch(Intent(this, GameActivity::class.java))
    }
    fun onChangeGamemode(){
        SessionConf.isPvC = !SessionConf.isPvC

        if (SessionConf.isPvC) {
            binding.gameMode.setText("PvC")
            binding.PlayerTwoNameField.visibility = View.INVISIBLE
        }else {
            binding.gameMode.setText("PvP")
            binding.PlayerTwoNameField.visibility = View.VISIBLE
        }
        updateStartingSideText()
    }
    fun onChangeStartingSide(){
        val startingSideLebel = binding.startingSideLabel
        val isPlayerOneStarting = !SessionConf.isPlayerOneStarting
        SessionConf.isPlayerOneStarting = isPlayerOneStarting
        updateStartingSideText()
    }

    fun updateStartingSideText(){
        binding.startingSideLabel.setText(
            if (SessionConf.isPlayerOneStarting) binding.PlayerOneNameField.text.toString()
            else (if (SessionConf.isPvC) "Computer"
            else binding.PlayerTwoNameField.text.toString())
        )
    }

    fun updateStartingSideName(){
        binding.startingSideLabel.text = (
            if (SessionConf.isPlayerOneStarting) binding.PlayerOneNameField.text.toString()
            else binding.PlayerTwoNameField.text.toString()
            ) + " stating"
    }

}