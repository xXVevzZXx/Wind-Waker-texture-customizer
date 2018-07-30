package de.piedev.core.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class Task
{
	public static ExecutorService POOL = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("Async Module %1$d").build());
}
