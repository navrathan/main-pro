package com.one.vo;

import java.util.Comparator;

public class Sort implements Comparator<Services> {

	@Override
	public int compare(Services o1, Services o2) {

		return Double.compare(o1.getDistance(), o2.getDistance());
	}

}
