# coding=utf-8
__author__ = 'Administrator'

import sys
import os

import unittest
import xmlrunner
import sdpTest.file
reload(sys)
# sys.setdefaultencoding('utf-8')



def getSuites(root):
    testSets = {"cases":["test_greeting"]}

    suites = unittest.TestSuite()

    for ts_name, ts in testSets.iteritems():
        ts_dir = "%s/%s" % (root, ts_name)
        print "ts_dir"+ts_dir
        sys.path.append(ts_dir)
        print "ts_dir = %s" % ts_dir
        for tc in ts:
            print tc
            module = __import__(tc, {}, {}, [], -1)
            print module
            suites.addTest(unittest.TestLoader().loadTestsFromModule(module))
            print suites
            pass


    return suites


if __name__ == "__main__":
    print 'executable'+sys.executable
    print sys.modules.keys()
    print sys.modules.values()   
    root =os.path.dirname(os.path.abspath(__file__));
    os.sys.path.append(root + os.sep + "..");
    print "root:"+root
    sdpTest.file.app_location =os.path.dirname(root) + os.sep
    print "file app_location " +sdpTest.file.get_app_loc()
    suites = getSuites(root)
    results = xmlrunner.XMLTestRunner(output='../../target/test_reports').run(suites)
