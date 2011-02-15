
object Main {
    def main(args: Array[String]) {
        val b = Board.fromString("-----\n-***-\n-----")
        println(b)
        println()
        println(b next)
    }
}

