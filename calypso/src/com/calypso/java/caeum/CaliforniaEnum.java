package com.calypso.java.caeum;

public enum CaliforniaEnum {
	SUMMER(1, 94536, 3234), WINTER(2, 55543, 4345);
	private CaliforniaEnum(int id, int zip, int pop) {
		// this for current class....non-static is not class level which is
		// object resource
		this.i = id;
		this.z = zip;
		this.p = pop;

	}

	private int i;
	private int z;
	private int p;
	public int getI() {
		return i;
	}
	public int getZ() {
		return z;
	}
	public int getP() {
		return p;
	}

	
	
}
