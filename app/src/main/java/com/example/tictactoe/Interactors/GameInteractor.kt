package com.example.tictactoe.Interactors

import com.example.tictactoe.Entities.AIActor
import com.example.tictactoe.Entities.BaseActor
import com.example.tictactoe.Entities.PlayerActor
import com.example.tictactoe.singletons.SessionConf
import java.util.LinkedList

class GameInteractor(
    val updateBoard:()->Unit,
    val onFinished:()->Unit
)
{
    val board : Array<IntArray> = Array(3){IntArray(3)}
    var isPlayerOneTurn:Boolean = SessionConf.isPlayerOneStarting
    val playerOneActor: BaseActor = PlayerActor(true)
    val playerTwoActor: BaseActor = if (SessionConf.isPvC)
        AIActor(false) else PlayerActor(false)
    var actionsLeft = 9


    fun pressAtPosition(value:Int, x:Int, y:Int){
        if (!isPositionsEmpty(x,y))
            return
        board[x][y] = value
        updateBoard()
        actionsLeft--
        if (actionsLeft <= 0
            || checkForEndCombat() != 0){
            onFinished.invoke()
            return
        }
        endTurn()
    }

    fun getEmptyPositions() : LinkedList<Pair<Int,Int>>{
        val list = LinkedList<Pair<Int,Int>>()
        repeat(9){
            if (board[it/3][it%3] == 0)
                list.add(Pair(it/3,it%3))
        }
        return list
    }

    fun isPositionsEmpty(x:Int, y:Int) : Boolean{
        if (board[x][y] == 0)
            return true
        return false
    }

    fun endTurn(){
        isPlayerOneTurn = !isPlayerOneTurn

        if (isPlayerOneTurn)
            playerOneActor.OnTurnStarted(this)
        else
            playerTwoActor.OnTurnStarted(this)
    }

    fun onGameStarted() {
        if (isPlayerOneTurn)
            playerOneActor.OnTurnStarted(this)
        else
            playerTwoActor.OnTurnStarted(this)
    }

    fun checkForEndCombat() : Int{
        val rowSums = listOf(
            sumRow(board, 0),
            sumRow(board, 1),
            sumRow(board, 2))
        val colSums = listOf(
            sumCol(board, 0),
            sumCol(board, 1),
            sumCol(board, 2))
        val diogonalSums = listOf(
            sumDigonal(board, true),
            sumDigonal(board, false)
        )
        if (rowSums.max() == 3
            || colSums.max() == 3
            || diogonalSums.max() == 3)
            return 1
        if (rowSums.min() == -3
            || colSums.min() == -3
            || diogonalSums.min() == -3)
            return -1
        return 0
    }

    fun sumRow(arr:Array<IntArray>, x:Int) : Int{
        return arr[x][0] + arr[x][1] + arr[x][2]
    }
    fun sumCol(arr:Array<IntArray>, y:Int) : Int{
        return arr[0][y] + arr[1][y] + arr[2][y]
    }
    fun sumDigonal(arr:Array<IntArray>, isAscending:Boolean) : Int{
        if (isAscending)
            return arr[0][0] + arr[1][1] + arr[2][2]
        else
            return arr[2][0] + arr[1][1] + arr[0][2]
    }
}