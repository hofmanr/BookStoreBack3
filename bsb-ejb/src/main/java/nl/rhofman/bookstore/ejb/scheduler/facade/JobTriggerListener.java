package nl.rhofman.bookstore.ejb.scheduler.facade;

import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import nl.rhofman.bookstore.ejb.scheduler.job.JobLauncher;
import nl.rhofman.bookstore.ejb.scheduler.service.InstanceService;

import java.util.concurrent.atomic.AtomicBoolean;

//@MessageDriven
public class JobTriggerListener implements MessageListener {

    @Inject
    private InstanceService instanceService;

    public JobTriggerListener() {
    }

    @Override
    public void onMessage(Message message) {
        AtomicBoolean handled = new AtomicBoolean(false);
        instanceService.executeBean(JobLauncher.class, it -> {
            if (it.handles("JOB1")) {
                handled.set(true);
                it.launch();
            }
        });

        if (!handled.get()) {
            System.out.println("No jobs found");
        }
    }
}
