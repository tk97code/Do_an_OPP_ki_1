package Test;

import java.util.ArrayList;
import java.util.List;

class mydata {
	private int a;
	private String b;

	public mydata(int a, String b) {
		this.a = a;
		this.b = b;
	}

	public void setdata(int a, String b) {
		this.a = a;
		this.b = b;
	}
	
	public void seta(int a) {
		this.a =a;
	}
	
	public int geta() {
		return a;
	}
}

public class test {
	public static void main(String[] args) {
		mydata d = new mydata(1, "hello");
		mydata d2 = new mydata(2, "asdf");
		mydata d3 = new mydata(3, "xvvvv");
		List<mydata> l = new ArrayList<>();
		l.add(d);
		l.add(d2);
		l.add(d3);
		
		for (mydata a: l) {
			a.seta(6);
		}
		
		for (mydata a: l) {
			System.out.println(a.geta());
		}
	}
}