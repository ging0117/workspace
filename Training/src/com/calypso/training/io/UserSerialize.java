package com.calypso.training.io;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class UserSerialize implements Serializable {

	public String name;
//transient ...will make sure never get transform
	public transient Integer ssn;

}
