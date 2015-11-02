package edu.uchicago.cs.energymon;

import java.math.BigInteger;

/**
 * A simple example.
 * 
 * @author Connor Imes
 */
public class EnergyMonJNIExample {

	public static void main(final String[] args) {
		int iterations = 10;
		if (args.length > 0) {
			iterations = Integer.parseInt(args[0]);
		}
		// create the EnergyMon object
		final EnergyMon em = new DefaultEnergyMonJNI();
		// initialize
		if (em.init() != 0) {
			throw new IllegalStateException("Failed to initialize");
		}
		// can now run other operations
		final String source = em.getSource();
		final BigInteger interval_us = em.getInterval();
		final long interval_ms = interval_us.longValue() / 1000;
		System.out.println("Reading from energymon source: " + source
				+ " with interval " + interval_us + " microseconds");
		for (int i = 0; i < iterations; i++) {
			try {
				Thread.sleep(interval_ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// read from source
			System.out.println("Read value: " + em.readTotal());
		}
		// clean up resources
		// (otherwise polling threads remain active and/or memory leaks
		em.finish();
	}
}
