/*
 * Creating threads using std::thread (requires C++ 11)
 * g++ test.cpp -std=c++0x -pthread
 */
#include <iostream>
#include <thread>
#include <condition_variable>

using namespace std;

std::condition_variable cond_var;
std::mutex mutex_var;

void thread1_handler()
{
    std::unique_lock<std::mutex> ulock(mutex_var);
    for(int i=0;i<3;i++)
    {
        cond_var.wait(ulock);
        cout << "Ping!" << endl;
        cond_var.notify_one();
    }
}

void thread2_handler()
{
    std::unique_lock<std::mutex> ulock(mutex_var);
    for(int i=0;i<3;i++)
    {
        cond_var.notify_one(); 
        cond_var.wait(ulock); 
        cout << "Pong!" << endl;
    }
}

int main()
{
    cout<<"Ready... Set... Go!"<<endl;
    std::thread t1(thread1_handler);
    std::thread t2(thread2_handler);
    t1.join();
    t2.join();
    cout<<"Done!"<<endl;
    return 0;
}
