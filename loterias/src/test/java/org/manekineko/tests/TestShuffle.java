package org.manekineko.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestShuffle {
	
	public static void main(String[] args) {
		List<Integer> ds = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			ds.add(i);
		}
		Collections.shuffle(ds, new Random(System.currentTimeMillis()));
		System.out.println(ds.subList(0, 10));
	}
}
