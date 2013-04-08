#!/usr/bin/env python

import threading

condition = threading.Condition()

def thread1_handler():
    global condition
    condition.acquire()
    for i in range(3):
        condition.wait()
        print 'Ping!'
        condition.notify()
    condition.release()


def thread2_handler():
    global condition
    condition.acquire()
    for i in range(3):
        condition.notify()
        condition.wait()
        print 'Pong!'
    condition.release()


def run_threads():
    thread1 = threading.Thread(target=thread1_handler)
    thread2 = threading.Thread(target=thread2_handler)
    print 'Ready... Set... Go!'
    thread1.start()
    thread2.start()
    # wait for ending of threads
    thread1.join()
    thread2.join()
    print 'Done!'

if __name__ == '__main__':
    run_threads()