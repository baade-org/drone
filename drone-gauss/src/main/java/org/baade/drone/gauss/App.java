package org.baade.drone.gauss;

import org.baade.drone.gauss.iir.Kernel;
import org.baade.drone.gauss.iir.KernelBuilder;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Kernel kernel = KernelBuilder.build(0.6f);
		System.out.println(kernel);
	}
}
