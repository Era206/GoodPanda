package goodpanda.scheduler;

import goodpanda.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sanjidaera
 * @since 14/12/24
 */
@Component
public class RiderAssignmentScheduler {

    @Autowired
    private RiderService riderService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void scheduleRiderAssignment() {
        System.out.println("Running Rider Assignment Scheduler...");
        riderService.assignRidersToOrders();
    }
}