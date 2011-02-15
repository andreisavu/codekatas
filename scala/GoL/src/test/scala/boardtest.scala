
import org.junit.Test
import org.scalatest.junit.{ShouldMatchersForJUnit, JUnitSuite}

class BoardTest extends JUnitSuite with ShouldMatchersForJUnit {

    @Test def testShowEmptyBoard() {
        val b = new Board(2, 2)
        b.mkString should be ("--\n--")
        b.get(1, 1) should be (false)
    }

    @Test def testSetAlive() {
        val b = new Board(2,2)
        b.set(1, 1, true)
        
        b.get(1, 1) should be (true)
        b.get(1, 2) should be (false)
    }
    
    @Test def testShowOneAlive() {
        val b = new Board(2, 2)
        b.set(1, 1, true)
        b.mkString should be ("*-\n--")
    }
    
    @Test def testNegativeSize() {
        intercept[IllegalArgumentException] {
            new Board(-1, 2)
        }
        intercept[IllegalArgumentException] {
            val b = new Board(1,2)
            b.set(-1, 2, true)
        }
    }

    @Test def testLoadFromString() {
        val empty = "--\n--"
        val b = Board.fromString(empty)
        b.mkString should be (empty)
    }

    @Test def testOneDies() {
        val b = Board.fromString("*-\n--")
        b.next().mkString should be ("--\n--")
    }

    @Test def testBlockStillLive() {
        val block = """|----
                       |-**-
                       |-**-
                       |----""".stripMargin
        val b = Board.fromString(block)
        b.next().mkString should be (block)
    }

    @Test def testOscillator() {
        val repr = """|---
                      |***
                      |---""".stripMargin
        val expected = """|-*-
                          |-*-
                          |-*-""".stripMargin
        val b = Board.fromString(repr)
        
        b.next().mkString should be(expected)
        b.next().next().mkString should be(repr)
    }

}

