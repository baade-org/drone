package org.baade.drone.gauss.iir;

public class KernelBuilder {

	public static Kernel build(float sigma) {
		Kernel kernel = new Kernel(sigma);
		float q, q2, q3;

		q = 0;

		if (sigma >= 2.5) {
			q = 0.98711f * sigma - 0.96330f;
		} else if ((sigma >= 0.5) && (sigma < 2.5)) {
			q = 3.97156f - 4.14554f * (float) Math.sqrt((double) 1 - 0.26891 * sigma);
		} else {
			q = 0.1147705018520355224609375f;
		}

		q2 = q * q;
		q3 = q * q2;
		
		kernel.setQ(q);
		kernel.setQ2(q2);
		kernel.setQ3(q3);

		kernel.getbArr()[0] = (1.57825f + (2.44413f * q) + (1.4281f * q2) + (0.422205f * q3));
		kernel.getbArr()[1] = ((2.44413f * q) + (2.85619f * q2) + (1.26661f * q3));
		kernel.getbArr()[2] = (-((1.4281f * q2) + (1.26661f * q3)));
		kernel.getbArr()[3] = ((0.422205f * q3));

		kernel.setB(1.0f - ((kernel.getbArr()[1] + kernel.getbArr()[2] + kernel.getbArr()[3]) / kernel.getbArr()[0]));
		kernel.setN(3);

		return kernel;
	}
}
