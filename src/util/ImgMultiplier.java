package util;

public class ImgMultiplier {
	private byte[] inputData;

	public ImgMultiplier(byte[] input) {
		inputData = input;
	}

	public byte[] grow() {
		
		int m = (int) Math.sqrt(inputData.length);
		byte[][] output = new byte[2*m][2*m];
		for (int i = 0; i < m; i++) {
			for(int j = 0; j < m; j++) {
				byte b = inputData[i+j];
				output[2*i][2*j] = output[2*i+1][2*j] = output[2*i][2*j+1] = output[2*i+1][2*j+1] = b;
			}
		}
		byte[] result = new byte[4*inputData.length];
		int offset = 0;
		for(byte[] row : output) {
			for(int i = 0; i < row.length; i++) {
				result[i+offset] = row[i];
			}
			offset += row.length;
		}
		return result;
	}

}
