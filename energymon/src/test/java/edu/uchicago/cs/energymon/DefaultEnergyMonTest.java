package edu.uchicago.cs.energymon;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for {@link DefaultEnergyMon}.
 * 
 * @author Connor Imes
 */
public class DefaultEnergyMonTest {

	@Test
	public void test_normal() {
		EnergyMon em = DefaultEnergyMon.create();
		assertNotNull("getSource", em.getSource());
		assertNotEquals("getInterval", 0, em.getInterval());
		assertTrue("readTotal", em.readTotal() >= 0);
		em.getPrecision();
		em.isExclusive();
		assertEquals("finish", 0, em.finish());
	}

	@Test
	public void test_multiple() {
		EnergyMon em1 = DefaultEnergyMon.create();
		EnergyMon em2 = DefaultEnergyMon.create();
		assertNotNull("getSource 1", em1.getSource());
		assertNotNull("getSource 2", em2.getSource());
		assertNotEquals("getInterval 1", 0, em1.getInterval());
		assertNotEquals("getInterval 2", 0, em2.getInterval());
		em1.getPrecision();
		em2.getPrecision();
		em1.isExclusive();
		em2.isExclusive();
		assertTrue("readTotal 1", em1.readTotal() >= 0);
		assertTrue("readTotal 2", em2.readTotal() >= 0);
		assertEquals("finish 1", 0, em1.finish());
		assertEquals("finish 2", 0, em2.finish());
	}

	@Test
	public void test_uninitialized() {
		EnergyMon em = DefaultEnergyMon.create();
		// behavior is undefined, but hopefully doesn't cause crashes
		em.getSource();
		em.getInterval();
		em.getPrecision();
		em.isExclusive();
		em.readTotal();
		em.finish();
	}

	@Test(expected = IllegalStateException.class)
	public void test_read_after_finish() {
		EnergyMon em = DefaultEnergyMon.create();
		assertEquals("finish", 0, em.finish());
		em.readTotal();
	}

	@Test(expected = IllegalStateException.class)
	public void test_source_after_finish() {
		EnergyMon em = DefaultEnergyMon.create();
		assertEquals("finish", 0, em.finish());
		em.getSource();
	}

	@Test(expected = IllegalStateException.class)
	public void test_interval_after_finish() {
		EnergyMon em = DefaultEnergyMon.create();
		assertEquals("finish", 0, em.finish());
		em.getInterval();
	}

	@Test(expected = IllegalStateException.class)
	public void test_finish_after_finish() {
		EnergyMon em = DefaultEnergyMon.create();
		assertEquals("finish", 0, em.finish());
		em.finish();
	}
}
