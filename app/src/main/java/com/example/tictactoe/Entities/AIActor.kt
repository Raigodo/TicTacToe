package com.example.tictactoe.Entities

import com.example.tictactoe.Interactors.GameInteractor

class AIActor(isPlayerOne:Boolean) : BaseActor(isPlayerOne){
    override fun OnTurnStarted(gameInteractor: GameInteractor) {
        val position = gameInteractor.getEmptyPositions().shuffled().first()
        gameInteractor.pressAtPosition(
            if (isPlayerOne) 1 else -1, position.first, position.second
        )
    }

}