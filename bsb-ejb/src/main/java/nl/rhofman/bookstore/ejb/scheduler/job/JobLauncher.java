package nl.rhofman.bookstore.ejb.scheduler.job;

public interface JobLauncher {

    void launch();
    boolean handles(String job);
}
