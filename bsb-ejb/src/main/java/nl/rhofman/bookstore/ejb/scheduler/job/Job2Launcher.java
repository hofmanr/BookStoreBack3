package nl.rhofman.bookstore.ejb.scheduler.job;

public class Job2Launcher implements JobLauncher {
    @Override
    public void launch() {
        System.out.println("Job2Launcher.launch");
    }

    @Override
    public boolean handles(String job) {
        return "JOB2".equals(job);
    }
}
