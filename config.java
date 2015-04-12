/**
 * Created by Rafa³ Rybak 194184 on 2015-04-11.
 */

/**
 * This is configuration for mine production server.
 * MAX - max number od tasks;
 * STORE_MAX - max capacity of a Store
 * MAX_WORKERS - max number of workers
 * MAX_CUSTOMERS - max number of customers
 * BOSS_SLEEP - Boss will be w8 ing for this * nextInt(9) ms
 * WORKER_SLEEP - worker will w8ing for this ms
 * CUSTOMER_SLEEP customer ------||------------
 * WORKER_FREE_TIME - Worker will get free time afer N works
 *
 */
public class config {
    public final int MAX = 5;
    public final int STORE_MAX = 100;
    public final int MAX_WORKERS = 2;
    public final int MAX_CUSTOMERS = 1;
    public final int BOSS_SLEEP = 1000;
    public final int WORKER_SLEEP = 2500;
    public final int CONSUMER_SLEEP = 5000;
    public final int WORKER_FREE_TIME = 10*1000;
    public final int WORKERS_CHANGE = 4000;
}
