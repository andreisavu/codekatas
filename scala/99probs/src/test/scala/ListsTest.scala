
import org.scalatest.FunSuite

class ListsTest extends FunSuite {

  test("P1. Get the last element from list") {
    def last[T](seq: List[T]): T = seq match {
      case x::Nil => x
      case x::xs => last(xs)
      case Nil => throw new NoSuchElementException
    }
    assert(last(List(1,2,3)) === 3)
    intercept[NoSuchElementException] {
      last(Nil)
    }
  }

  test("P2. Get the last but one element from a list") {
    def penultimate[T](seq: List[T]): T = seq match {
      case a :: b :: Nil => a
      case a :: Nil => throw new NoSuchElementException
      case a :: rest => penultimate(rest)
      case Nil => throw new NoSuchElementException
    }
    assert(penultimate(List(1,2,3,4)) === 3)
    intercept[NoSuchElementException] {
      penultimate(List(1))
    }
    intercept[NoSuchElementException] {
      penultimate(Nil)
    }
  }

  test("P3. Find the nth element of a list") {
    def nth[T](index: Int, seq: List[T]): T = {
      def _nth(i: Int, seq: List[T]): T = seq match {
        case head :: tail => if (i == index) head else _nth(i + 1, tail)
        case Nil => throw new IndexOutOfBoundsException
      }
      _nth(0, seq)
    }
    assert(nth(2, List(1,2,3)) === 3)
    intercept[IndexOutOfBoundsException] {
      nth(10, 1 :: 2 :: Nil)
    }
  }

  test("P4. Find the number of elements of a list") {
    def length[T](seq: List[T]): Int  = {
      def _length(offset: Int, seq: List[T]): Int = seq match {
        case x :: rest => _length(offset + 1, rest)
        case Nil => offset
      }
      _length(0, seq)
    }
    assert(length(Nil) === 0)
    assert(length(1 :: 2 :: Nil) === 2)
  }

  test("P5. Reverse a list") {
    def reverse[T](seq: List[T]): List[T] = seq match {
      case head :: tail => reverse(tail) ::: List(head)
      case Nil => Nil
    }
    assert(reverse(Nil) === Nil)
    assert(reverse(1 :: 2 :: Nil) === 2 :: 1 :: Nil)
  }

  test("P6. Find if a list is a palindrome") {
    def reverse[T](seq: List[T]): List[T] = seq match {
      case head :: tail => reverse(tail) ::: List(head)
      case Nil => Nil
    }
    def palindome[T](seq: List[T]): Boolean = seq == reverse(seq)

    assert(palindome(Nil))
    assert(palindome(1 :: 2 :: 1 :: Nil))
    assert(!palindome(1 :: 2 :: Nil))
  }

  test("P7. Flatten a list of lists") {
    def flatten(seq: List[Any]): List[Any] = seq match {
      case (head: List[Any]) :: tail => flatten(head) ::: flatten(tail)
      case head :: tail => List(head) ::: flatten(tail)
      case Nil => Nil

    }
    assert(flatten(Nil) === Nil)
    assert(flatten(List(1, List(2,3))) === List(1,2,3))
    assert(flatten(List(1, List(List(2,3),4))) == List(1,2,3,4))
  }

  test("P8. Eliminate consecutive duplicates of a list of elements") {
    def compress[T](seq: List[T]): List[T] = seq match {
      case a :: b :: rest => if (a == b) compress(a :: rest) else a :: compress(b :: rest)
      case a :: Nil => List(a)
      case Nil => Nil
    }
    assert(compress(Nil) == Nil)
    assert(compress(1 :: 1 :: 2 :: Nil) == List(1,2))
    assert(compress(1 :: 3 :: Nil) == 1 :: 3 :: Nil)
    assert(compress(1 :: 2 :: 2 :: Nil) === 1 :: 2 :: Nil)
    assert(compress(3 :: 3 :: 3 :: Nil) === 3 :: Nil)
  }

  def pack[T](seq: List[T]): List[List[T]] = {
    def _pack(seq: List[T], acc: List[T]): List[List[T]] =  seq match {
      case a :: b :: rest if (a == b) => _pack(b :: rest, a :: acc)
      case a :: b :: rest => ((a :: acc) :: Nil) ::: _pack(b :: rest, Nil)
      case a :: Nil => (a :: acc) :: Nil
      case Nil => acc :: Nil
    }
    _pack(seq, Nil)
  }

  test("P9. Pack consecutive elements into sublists") {
    assert(pack(1 :: 2 :: Nil) === List(List(1), List(2)))
    assert(pack(1 :: 1 :: 2 :: Nil) === List(List(1,1), List(2)))
    assert(pack(1 :: 1 :: 2 :: 3 :: 3 :: Nil) ===
      List(List(1,1), List(2), List(3,3)))
  }

  test("P9-2. Pack consecutive elemnts using reduceLeft") {
    def pack[T](seq: List[T]): List[List[T]] = {
      var result: List[List[T]] = Nil
      seq.foreach(el => {
        result match {
          case first :: tail =>
            if (first.head == el) result = (el :: first) :: tail
            else result = List(el) :: result
          case Nil => result = List(List(el))
        }
      })
      result.reverse
    }
    assert(pack(1 :: 2 :: Nil) === List(List(1), List(2)))
    assert(pack(1 :: 1 :: 2 :: Nil) === List(List(1,1), List(2)))
    assert(pack(1 :: 1 :: 2 :: 3 :: 3 :: Nil) ===
      List(List(1,1), List(2), List(3,3)))
  }

  test("P10. Run-length encoding of a list") {
    def rle[T](seq: List[T]): List[(Int, T)] = {
      pack(seq).map(e => (e.length, e.head))
    }
    assert(rle(1 :: 1 :: 2 :: Nil) === (2, 1) :: (1, 2) :: Nil)
  }

  test("P11. Modified run-length encoding of a list") {
    def rle[T](seq: List[T]): List[Any] = {
      pack(seq).map(e => if (e.length > 1) (e.length, e.head) else e.head)
    }
    assert(rle(1 :: 1 :: 2 :: Nil) === (2, 1) :: 2 :: Nil)
  }

  test("P12. Decode a run-length encoded list") {
    def decode[T](seq: List[(Int, T)]): List[T] = {
      def _decode(seq: List[(Int, T)], count: Int, el: T): List[T] = {
        if (count > 0) el :: _decode(seq, count - 1, el)
        else seq match {
          case head :: tail => _decode(tail, head._1, head._2)
          case Nil => Nil
        }
      }
      if (seq.isEmpty) Nil else _decode(seq.tail, seq.head._1, seq.head._2)
    }
    assert(decode((2, 1) :: Nil) === List(1, 1))
    assert(decode(Nil) === Nil)
  }

  test("P13. Run-length encoding of a list (direct solution)") {
    def rle[T](seq: List[T]): List[(Int, T)] = {
      var result: List[(Int, T)] = Nil
      seq.foreach(el => {
        result match {
          case first :: tail =>
            if (first._2 == el) result = (first._1 + 1, el) :: tail
            else result = (1, el) :: result
          case Nil => result = List((1, el))
        }
      })
      result.reverse
    }
    assert(rle(Nil) === Nil)
    assert(rle(1 :: 1 :: 2 :: Nil) === (2, 1) :: (1, 2) :: Nil)
  }

  test("P14. Duplicate elements of a list") {
    def duplicate[T](seq: List[T]): List[T] = seq match {
      case head :: tail => head :: head :: duplicate(tail)
      case Nil => Nil
    }
    assert(duplicate(1 :: 2 :: Nil) === List(1, 1, 2, 2))
  }

  test("P15. Duplicate the elements of a list a given number of times.") {
    def duplicate[T](n: Int, seq: List[T]): List[T] = 
        seq.map(el => (1 to n).map(_ => el).toList).reduceRight(_ ::: _).toList
        
    assert(duplicate(3, 1 :: Nil) === List(1,1,1))
    assert(duplicate(2, 1 :: 2 :: Nil) === List(1, 1, 2, 2))
  }
  
  test("P16. Drop every nth element from a list.") {
    def drop[T](index: Int, seq: List[T]): List[T] = {
        ((1 to seq.length) zip seq).filter(_._1 % index != 0).toList.map(_._2)
    }
    assert(drop(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 
        'j, 'k)) === List('a, 'b, 'd, 'e, 'g, 'h, 'j, 'k))
  }
  
  test("P17. Split a list into two parts") {
    def split[T](point: Int, seq: List[T]): List[List[T]] = {
        val pairs = seq zip (1 to seq.length)
        def filter(cond: ((T, Int)) => Boolean) = 
            pairs.filter(cond).toList.map(x => x._1)
        List(filter(_._2 <= point), filter(_._2 > point))
    }
    assert(split(1, List(1,2,3)) === List(1 :: Nil, 2 :: 3 :: Nil))
  }

  test("P18. Extract a slice from a list") {
    def slice[T](start: Int, stop: Int, seq: List[T]): List[T] = 
      seq.zip(0 until seq.length).filter(x => x._2 >= start && x._2 < stop)
        .toList.map(_._1)
        
    assert(slice(3, 7, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 
      'h, 'i, 'j, 'k)) === List('d, 'e, 'f, 'g))
  }

  test("P19. Rotate a list N placs to the left") {
    def rotate[T](steps: Int, seq: List[T]): List[T] = {
      Nil
    }
  }
  
  /* P20 - easy; same pattern; zip with sequence and filter */
  
  
}
