""" P08 (**) Eliminate consecutive duplicates of list elements. """

import unittest

def compress(seq):
  """ Compress by eliminating consecutive duplicates """
  if seq:
    prev = seq[0]
    yield prev

    for el in seq[1:]:
      if el != prev:
        yield el
        prev = el

class RemoteDuplicatesTest(unittest.TestCase):
  def test(self):
    test_cases = [
      [], [],
      [1], [1],
      [1, 1], [1],
      [1, 1, 2], [1, 2],
      [1, 2, 1, 1], [1, 2, 1],
      [None, None, 1], [None, 1]
    ]
    for raw, expected in self.pairs(test_cases):
      actual = list(compress(raw))
      self.assertEquals(actual, expected,
        "compress(%s) = %s != %s" % (raw, actual, expected))

  def pairs(self, seq):
    """ Generate consecutive pairs from a sequence """
    previous = None
    for el in seq:
      if previous is not None:
        yield (previous, el)
        previous = None

      else:
        previous = el

if __name__ == '__main__':
  unittest.main()

