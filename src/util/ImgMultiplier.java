package util;

public class ImgMultiplier {
	// even lines are flipped
	private byte[] inputData;

	public ImgMultiplier(byte[] input) {
		inputData = input;
	}

	public byte[] grow() {
		byte[] output = new byte[4 * inputData.length];
		int m = (int) Math.sqrt(inputData.length);
		for (int i = 0; i < m; i++) {
			for (int j = i * m; j < (i + 1) * m; j++) {
				byte b = inputData[j]; // pixel
				int o = j * 2; // offset
				output[o] = output[o + 1] = output[o + m] = output[o + m + 1] = b;
			}
		}
		return output;
	}

}
