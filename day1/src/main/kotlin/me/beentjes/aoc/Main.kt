package me.beentjes.aoc;


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val input = Main::class.java.getResource("/input.txt")?.readText()
            val elves = input?.split("\n\n");
            val elfWithMostCalories = elves?.mapIndexed { index, caloriesPerElf ->
                val calories = caloriesPerElf.split("\n").map { it.toInt() }.reduce(Int::plus)
                index to calories
            }?.sortedByDescending { it.second }!!
            val topThreeCalories = elfWithMostCalories[0].second + elfWithMostCalories[1].second +elfWithMostCalories[2].second

            println("Elf  with most calories: $topThreeCalories")

        }

    }
}