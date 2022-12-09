package Problems;

import CustomCollections.LinkedListQueue;

public class JosephusProblem {

    private final int N, m;
    private final LinkedListQueue<Integer> queue, solutionQueue;

    public JosephusProblem(int m, int N) {
        this.N = N; // Number of persons
        this.m = m; // mth termination
        this.queue = new LinkedListQueue<Integer>();
        this.solutionQueue = new LinkedListQueue<Integer>();
    }

    public int solve() {
        int lastManStanding = -1;

        // Add all persons to the queue
        for (int i = 0; i < this.N; i++)
            this.queue.enqueue(i);

        while (!this.queue.isEmpty()) {
            // Skip m-1 persons
            for (int i = 0; i < this.m - 1; i++) {
                if (!this.queue.isEmpty())
                    this.queue.enqueue(this.queue.dequeue());
            }
            // Terminate mth person
            int person = this.queue.dequeue();
            this.solutionQueue.enqueue(person);
            lastManStanding = person;
        }
        display(solutionQueue);
        return lastManStanding;
    }

    private void display(LinkedListQueue<Integer> aQueue) {
        for (Integer i : aQueue)
            System.out.print(" " + i);
        System.out.println();
    }
}