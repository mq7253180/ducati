package com.quincy.core.test;

public class Algorithm {
	private static int[] soundOff(int n, int[] array) {
		if(array.length<n) {
			if(array.length==1) {
				return array;
			} else {
				int[] indexes = new int[(n/array.length)+array.length];
				for(int i=0;i<indexes.length;) {
					for(int j=0;j<array.length;i++,j++) {
						indexes[i] = j;
					}
				}
				int toRemoveIndex = indexes[n-1];
				int[] temp = new int[array.length-1];
				for(int i=0,j=0;i<array.length;i++) {
					if(i!=toRemoveIndex) {
						temp[j] = array[i];
						j++;
					}
				}
				return temp;
			}
		} else {
			int remainder = array.length%n;
			int[] temp = new int[array.length-(array.length/n)];
			for(int i=0;i<remainder;i++) {
				temp[i] = array[array.length-remainder+i];
			}
			for(int i=0,j=remainder;i<array.length;i++) {
				if(i%n!=0) {
					temp[j] = array[i];
					j++;
				}
			}
			return soundOff(n, temp);
		}
	}
	
	public static int getSoundOff(int n, int[] array) {
		return soundOff(n, array)[0];
	}

	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		System.out.println(getSoundOff(5, array));
	}
}
