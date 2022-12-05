package me.beentjes.aoc;

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        // load input.csv into a list of strings
        val rucksacksInput = App::class.java.getResource("/input.txt")?.readText()?.split("\n") ?: emptyList()
        val groupOfElves = rucksacksInput.chunked(3)
        val badges : MutableList<Char> = mutableListOf()
        groupOfElves.forEach { rucksacks ->
            val rucksacksAsCharArray = rucksacks.map { it.toCharArray() }
            val first = rucksacksAsCharArray[0].toSet()
            val second = rucksacksAsCharArray[1].toSet()
            val third = rucksacksAsCharArray[2].toSet()
            first.intersect(second).intersect(third).forEach { badges += it }
        }
        val errorItems: MutableList<Char> = mutableListOf()

        rucksacksInput.forEachIndexed { index, rucksack ->
            val middle = rucksack.length / 2
            val firstHalf = rucksack.substring(0, middle).toList()
            val secondHalf = rucksack.substring(middle).toList()
            val commonChars = firstHalf.intersect(secondHalf.toSet())
            commonChars.forEach(errorItems::add)

        }

        println("total prio of wrong placed items: ${errorItems.sumOf { it.aocCode() }}. Total score for the badges: ${badges.sumOf { it.aocCode() }}")
    }

    private fun Char.aocCode(): Int = when (this) {
        in 'a'..'z' -> this.code - 'a'.code + 1
        in 'A'..'Z' -> this.code - 'A'.code + 1 + 26
        else -> 0
    }
}