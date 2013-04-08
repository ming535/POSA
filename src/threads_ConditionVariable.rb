require 'thread'

mutex = Mutex.new
resource = ConditionVariable.new

a = Thread.new {
  mutex.synchronize {
    for i in 1..3
        resource.wait(mutex)
        puts "Ping!"
        resource.signal
    end
  }
}

b = Thread.new {
  mutex.synchronize {
    for i in 1..3
        resource.signal
        resource.wait(mutex) 
        puts "Pong!"
    end
  }
}

puts "Ready... Set... Go!"
a.join
b.join
puts "Done!"
