package com.hce.ducati.o;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Params {
	private String a;
	private String b;

	public String toString() {
		return a+"------"+b;
	}
}
