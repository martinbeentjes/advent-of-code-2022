package me.beentjes.aoc;


enum class Move(
    val opponentMove: Char,
    val playerMove: Char
) {
    ROCK('A', 'X'), PAPER('B', 'Y'), SCISSORS('C', 'Z');

    fun getPoint(): Int = when (this) {
        ROCK -> 1
        PAPER -> 2
        SCISSORS -> 3
        else -> {
            throw IllegalArgumentException("Unknown move")
        }
    }

    companion object {
        fun fromOpponentMove(opponentMove: String): Move {
            return values().first { it.opponentMove.toString() == opponentMove }
        }

        fun fromPlayerMove(playerMove: String): Move {
            return values().first { it.playerMove.toString() == playerMove }
        }

        fun getWinningMoveFromOpponentMove(opponentMove: Move): Move {
            return when (opponentMove) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
        }

        fun getLosingMoveFromOpponentMove(opponentMove: Move): Move {
            return when (opponentMove) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
        }

        fun getDrawMoveFromOpponentMove(opponentMove: Move): Move = opponentMove
    }
}

data class Game(
    val playerMove: Move,
    val opponentMove: Move
) {
    fun isPlayerWinner(): Boolean = when (playerMove) {
        Move.ROCK -> opponentMove == Move.SCISSORS
        Move.PAPER -> opponentMove == Move.ROCK
        Move.SCISSORS -> opponentMove == Move.PAPER
    }

    fun isGameDraw(): Boolean = playerMove == opponentMove

    fun isOpponentWinner(): Boolean = !isPlayerWinner() && !isGameDraw()

    fun getGameScore(): Int = when {
        isPlayerWinner() -> playerMove.getPoint() + 6
        isOpponentWinner() -> playerMove.getPoint() + 0
        else -> playerMove.getPoint() + 3
    }
}

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Every line is a game
            // The first character is the move the opponent made
            // The second character is the move the player made

            // read input from file input.csv
            Main::class.java.getResourceAsStream("/input.txt")
                ?.bufferedReader()
                ?.readLines()
                ?.map { it.split(' ') }
                ?.map { moves: List<String> ->
                    if (moves.size != 2) {
                        throw IllegalArgumentException("Invalid input")
                    }
                    val opponentMove = Move.fromOpponentMove(moves[0])
                    val playerMove = when (moves[1]) {
                        "X" -> Move.getLosingMoveFromOpponentMove(opponentMove)
                        "Y" -> Move.getDrawMoveFromOpponentMove(opponentMove)
                        "Z" -> Move.getWinningMoveFromOpponentMove(opponentMove)
                        else -> throw IllegalArgumentException("Unknown move")
                    }
                    Game(playerMove, opponentMove)
                }?.sumOf { it.getGameScore() }?.also { println(it) }
//            println("Game score = ${endScore ?: "null"}")


        }

    }
}