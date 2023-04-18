package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.tictactoe.Interactors.GameInteractor
import com.example.tictactoe.databinding.ActivityGameBinding
import com.example.tictactoe.singletons.SessionConf

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private val gameInteractor:GameInteractor = GameInteractor(
        { updateBoard() }, { viewResults() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialWork()
        gameInteractor.onGameStarted()
    }

    fun initialWork(){
        binding.playerOneName.text = SessionConf.playerOneName
        binding.playerTwoName.text = SessionConf.playerTwoName

        binding.endCombatBtn.setOnClickListener(){onEndCombat()}


        binding.gridCellTl.setOnClickListener(){onCellClicked(it)}
        binding.gridCellTc.setOnClickListener(){onCellClicked(it)}
        binding.gridCellTr.setOnClickListener(){onCellClicked(it)}
        binding.gridCellCl.setOnClickListener(){onCellClicked(it)}
        binding.gridCellCc.setOnClickListener(){onCellClicked(it)}
        binding.gridCellCr.setOnClickListener(){onCellClicked(it)}
        binding.gridCellBl.setOnClickListener(){onCellClicked(it)}
        binding.gridCellBc.setOnClickListener(){onCellClicked(it)}
        binding.gridCellBr.setOnClickListener(){onCellClicked(it)}
    }

    fun onCellClicked(cell: View){
        val value = if (gameInteractor.isPlayerOneTurn) 1 else -1
        Log.d("main", value.toString())
        when (cell.id){
            binding.gridCellTl.id -> gameInteractor.pressAtPosition(value, 0,0)
            binding.gridCellTc.id -> gameInteractor.pressAtPosition(value,0,1)
            binding.gridCellTr.id -> gameInteractor.pressAtPosition(value,0,2)

            binding.gridCellCl.id -> gameInteractor.pressAtPosition(value,1,0)
            binding.gridCellCc.id -> gameInteractor.pressAtPosition(value,1,1)
            binding.gridCellCr.id -> gameInteractor.pressAtPosition(value,1,2)

            binding.gridCellBl.id -> gameInteractor.pressAtPosition(value,2,0)
            binding.gridCellBc.id -> gameInteractor.pressAtPosition(value,2,1)
            binding.gridCellBr.id -> gameInteractor.pressAtPosition(value,2,2)
        }
    }

    fun updateBoard(){
        updateCell(binding.gridCellTl, 0,0)
        updateCell(binding.gridCellTc, 0,1)
        updateCell(binding.gridCellTr, 0,2)
        updateCell(binding.gridCellCl, 1,0)
        updateCell(binding.gridCellCc, 1,1)
        updateCell(binding.gridCellCr, 1,2)
        updateCell(binding.gridCellBl, 2,0)
        updateCell(binding.gridCellBc, 2,1)
        updateCell(binding.gridCellBr, 2,2)
    }

    fun updateCell(cell:ImageView, x:Int, y:Int){
        val valueAtPos:Int = gameInteractor.board[x][y]
        if (valueAtPos == 1){
            cell.setImageResource(R.drawable.gridicon_circle)
        }else if (valueAtPos == -1){
            cell.setImageResource(R.drawable.gridicon_cross)
        }
    }

    fun viewResults(){
        binding.combatResultWindow.visibility = View.VISIBLE
        val result = gameInteractor.checkForEndCombat()
        var headerText:String
        if (result == 0) headerText = "Draw"
        else if (result == 1) headerText = SessionConf.playerOneName + " Win"
        else headerText = SessionConf.playerTwoName + " Win"
        binding.resultWindowHeader.text = headerText
    }

    fun onEndCombat(){
        finish()
    }
}