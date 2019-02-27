This file provies general instructions for operation of the assignment

BASIC FLOW

1. The crude code is implementated in engine.java rest of the file contains the code as per the instructions
2. vmgen to create test file(arguements: max pageno, number of references made, file name)
3. vmsim runs the code according to your requirements(arguements: physical frame size, input file , method{"opt","lru","fifo"})
4. vmstat runs the code and generates file called vmrates.data(arguements: minFrames,maxFrames,increament,input filename)



Follow the following instrcutions :
  1. Compile the all the codes first.
    -- javac vmgen.java
    -- javac vmstat.java
    -- javac vmsim.java
    -- javac engine.java
  2. arguements are provide above for each file
  3. to run a file with arguements typeIn command: java <file_name> <args_1> <args_2> <args_3>
  4. Sample cases to run
    -- java vmgen 100 10 test.txt
    -- java vmsim 3 test.txt opt
    -- java vmstat 2 5 1 test.txt