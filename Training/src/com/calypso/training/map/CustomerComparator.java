package com.calypso.training.map;

import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer> {

	@Override
	public int compare(Customer o1, Customer o2) {
		// TODO Auto-generated method stub
		return o1.ssn-o2.ssn;
	}

}
