package com.example.tictactoe.Entities

import com.example.tictactoe.Interactors.GameInteractor

abstract class BaseActor(val isPlayerOne:Boolean) {
    abstract fun OnTurnStarted(gameInteractor: GameInteractor)
}