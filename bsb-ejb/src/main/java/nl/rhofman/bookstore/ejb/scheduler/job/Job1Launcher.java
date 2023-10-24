package nl.rhofman.bookstore.ejb.scheduler.job;

public class Job1Launcher implements JobLauncher {
    @Override
    public void launch() {
        System.out.println("Job1Launcher.launch");
    }

    @Override
    public boolean handles(String job) {
        return "JOB1".equals(job);
    }
}
