package pl.edu.agh.kis.pz1;


public class Reader extends Thread{
    private final int number;
    private final Library library;

    public Reader(int number, Library library){
        this.number = number;
        this.library = library;
    }

    @Override
    public void run(){
        while (library.keepWorking()){
            try {
                library.requestRead(number);
                sleep(1500);
                library.finishRead(number);
                sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Reader run InterruptedException");
                library.stopThreads();
            }
        }
    }
}
