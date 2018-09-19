package br.com.redu.util;

public final class DaemonThread extends Thread {
	public DaemonThread(Runnable runnable) {
		super(runnable);

		setDaemon(true);
	}
}
