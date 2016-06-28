# coding=utf-8
__author__ = 'Administrator'

import unittest
import xmlrunner
import sys

sys.path.insert(0, '..')

import sdpTest.http as cofHttp

class test_greeting(unittest.TestCase):
    def setUp(self):
        print 'init'
        
    def test_greeting_ok(self):      
        print "It is just a sample but do nothing"
        self.assertTrue(False, "hello")
        
    def test_greeting_ok2(self):      
        print "It is just a sample but do nothing"
        self.assertTrue(False, "hello")
        
        
#     def test_person_create(self):
#         p = PersonBaseHttp()
#         p.create()   
#         print "test_person_create pass"
if __name__ == "__main__":
    unittest.main(verbosity=2, exit=False)
