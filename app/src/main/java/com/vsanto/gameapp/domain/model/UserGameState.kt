package com.vsanto.gameapp.domain.model

enum class UserGameState(val code: Int) {
    UNSELECTED(0),
    WANT(1),
    PLAYING(2),
    PLAYED(3);

    companion object {
        fun toCode(state: UserGameState): Int = state.code
        fun toState(code: Int): UserGameState = enumValues<UserGameState>()[code]
    }

}