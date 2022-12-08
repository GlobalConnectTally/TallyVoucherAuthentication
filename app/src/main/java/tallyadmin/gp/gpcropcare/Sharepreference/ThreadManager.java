package tallyadmin.gp.gpcropcare.Sharepreference;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager
{
    private static ThreadManager instance;
    private static ExecutorService executorService;
    private final Context mContext;

    public ThreadManager(Context context){
        this.mContext = context;
        executorService = getExecutorService();
    }

    public static synchronized ThreadManager getInstance(Context context){
        if (instance == null){
            instance = new ThreadManager(context);
        }
        return instance;
    }

    private ExecutorService getExecutorService() {
        if(executorService == null){
            int num = Runtime.getRuntime().availableProcessors() * 10;
            executorService = Executors.newFixedThreadPool(num);
        }
        return  executorService;
    }

    public void executeTask(Runnable runnable){
        executorService.execute(runnable);
    }
}
