package com.ja.callables;

public interface Recycler<Chromosome> {
	/**
	 * Called for each chromosome of the last population before it is cleared.
	 * @param chromosome The chromosome to recycle
	 */
	public void recylce(Chromosome chromosome);
}
