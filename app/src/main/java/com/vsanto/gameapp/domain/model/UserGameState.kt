package com.vsanto.gameapp.domain.model

enum class UserGameState(val code: Int) {
    WANT(0),
    PLAYING(1),
    PLAYED(2);

    companion object {
        fun toCode(state: UserGameState): Int = state.code
        fun toState(code: Int): UserGameState = enumValues<UserGameState>()[code]
    }

}