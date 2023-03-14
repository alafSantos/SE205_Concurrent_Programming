public class BoundedBufferMain {

    public static void main(String[] args) {
        BoundedBuffer buffer;

        // Check the arguments of the command line
        if (args.length != 1) {
            System.out.println("PROGRAM FILENAME");
            System.exit(1);
        }
        Utils.init(args[0]);

        Consumer consumers[] = new Consumer[(int) Utils.nConsumers];
        Producer producers[] = new Producer[(int) Utils.nProducers];

        // Create a buffer
        if (Utils.sem_impl == 0)
            buffer = new NatBoundedBuffer(Utils.bufferSize);
        else
            buffer = new SemBoundedBuffer(Utils.bufferSize);

        // Create producers and then consumers
        // Create consumers
        for (int i = 0; i < (int) Utils.nConsumers; i++) {
            consumers[i] = new Consumer(i, buffer);
            Thread objt = new Thread(consumers[i]);
            objt.start();
            try {
                objt.join();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Create producers
        for (int i = 0; i < (int) Utils.nProducers; i++) {
            producers[i] = new Producer(i, buffer);
            Thread objt = new Thread(producers[i]);
            objt.start();
            try {
                objt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
