package de.piedev.core.event;

public interface Cancellable
{
	public boolean isCancelled();
	public void setCancelled(boolean cancelled);
}
