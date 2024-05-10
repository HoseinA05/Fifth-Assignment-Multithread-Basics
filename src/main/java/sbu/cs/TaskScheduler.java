package sbu.cs;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        // ------------------------- You don't need to modify this part of the code -------------------------
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        // ------------------------- You don't need to modify this part of the code -------------------------

        @Override
        public void run() {
            try {
                Thread.sleep(this.processingTime);
            } catch (InterruptedException ignored) {}
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
        }

        @Override
        public String toString() {
            return "Task(" + this.taskName + ", " + this.processingTime + ")";
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<String> finishedTasks = new ArrayList<>();

        // Sorting the given tasks based on the processing time.
        ArrayList<Task> prioritizedTasks = new ArrayList<>(tasks);
        prioritizedTasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return Integer.compare(t2.processingTime, t1.processingTime);
            }
        });

        for(Task t: prioritizedTasks){
            Thread thread = new Thread(t);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException ignored) {}
            finishedTasks.add(t.taskName);
        }

        /*
        TODO
            Create a thread for each given task, And then start them based on which task has the highest priority
            (highest priority belongs to the tasks that take more time to be completed).
            You have to wait for each task to get done and then start the next task.
            Don't forget to add each task's name to the finishedTasks after it's completely finished.
         */

        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new Task("A", 100));
        tasks.add(new Task("B", 150));
        tasks.add(new Task("C", 200));
        tasks.add(new Task("E", 130));
        tasks.add(new Task("F", 3000));

        TaskScheduler.doTasks(tasks);
    }
}
