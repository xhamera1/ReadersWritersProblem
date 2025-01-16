package pl.edu.agh.kis.pz1;



public class Writer extends Thread {
    private final int number;
    private final Library library;

    public Writer(int number, Library library) {
        this.number = number;
        this.library = library;
    }

    @Override
    public void run() {
        while (library.keepWorking()){
            try {
                library.requestWrite(number);
                sleep(2000);
                library.finishWrite(number);
                sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Writer run InterruptedException");
                library.stopThreads();
            }
        }
    }

}
