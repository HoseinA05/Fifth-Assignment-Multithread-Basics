- 1st Question :  
>Thread was interrupted!\
Thread will be finished here!!!

- 2nd Question :  
The run methode will be like any other method we write and will be run by the main Thread, thus the output will be :
>Running in: main

- 3rd Question:  
The join method puts the Main thread in sleep until the selected thread finishes its task and after the task is done (or just have finished without completing it) it produces an interruptedException and comes back to the Main Thread.  
the output will be : 
>Running in: Thread-0\
Back to: main