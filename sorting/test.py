#! /usr/bin/env python

import unittest
import random

import sort

class BaseSortTest(object):

    def test_empty_sequence(self):
        self.assertEqual(self.sorted([]), [])

    def test_single_element(self):
        self.assertEqual(self.sorted([1]), [1])

    def test_multiple_numbers(self):
        self.assertEqual(self.sorted([3,4,1]), [1,3,4])

    def test_in_reverse_order(self):
        self.assertEqual(self.sorted(range(100, 1, -1)), range(2, 101))

    def test_random_sequence(self):
        seq = [random.randrange(100) for _ in range(1,10)]
        expected = sorted(seq)
        self.assertEqual(self.sorted(seq), expected)

class InsertionSortTest(unittest.TestCase, BaseSortTest):
    sorted = lambda _, seq: sort.insertion(seq)


if __name__ == '__main__':
    unittest.main()

