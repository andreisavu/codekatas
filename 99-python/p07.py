""" P07 (**) Flatten a nested list structure. """

import unittest

def flatten(seq):
  def reducer(a, b):
    if isinstance(b, list):
      return a + flatten(b)
    return a + [b]
  return reduce(reducer, seq, [])

class FlattenTest(unittest.TestCase):
  def test(self):
    assert flatten([]) == []
    assert flatten([1, 2]) == [1, 2]
    assert flatten([[1, 2]]) == [1, 2]
    assert flatten([[1, 2], [3]]) == [1, 2, 3]
    assert flatten([[1, 2], 3]) == [1, 2, 3]
    assert flatten([[1, [2, 3]], 4]) == [1, 2, 3, 4]

unittest.main()

