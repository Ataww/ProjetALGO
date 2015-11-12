package compresseur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import reader.RawReader;
import util.ImgMultiplier;
import util.RawWriter;

public class App {

	public App() {

	}

	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("not enough arguments, expecting mode and image's path");
		} else if (!(args[0].equals("-r") || args[0].equals("-i") || args.equals("-g"))) {
			throw new IllegalArgumentException("invalid mode, -i or -r expected");
		}
		if (!new File(args[1]).exists()) {
			throw new IllegalArgumentException("invalid image path, does not exists");
		}
		if (args[0].equals("-g")) {
			try {
				RawReader reader = new RawReader(args[1]);
				reader.read(false);
				ImgMultiplier mult = new ImgMultiplier(reader.getData());
				byte[] output = mult.grow();
				String[] fileSegments = args[1].split(".");
				StringBuilder sb = new StringBuilder(fileSegments[0]);
				for (int i = 1; i < fileSegments.length - 1; i++) {
					sb.append("." + fileSegments[i]);
				}
				sb.append("_g.");
				sb.append(fileSegments[fileSegments.length - 1]);
				RawWriter writer = new RawWriter(sb.toString());
				writer.write(output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				RawReader rawReader = new RawReader(args[1]);
				Rouleau compresseur = new Rouleau(buildList(rawReader.getData()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<Pixel> buildList(byte[] data) {
		List<Pixel> pixels = new ArrayList<>(data.length);
		for (byte b : data) {
			pixels.add(new Pixel(b));
		}
		return pixels;
	}

}
