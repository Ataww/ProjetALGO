package compresseur;

import java.io.File;
import java.io.IOException;

import reader.RawReader;
import util.ImgMultiplier;
import util.RawWriter;
import util.SegWriter;

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
				rawReader.read(true);
				Rouleau compresseur = new Rouleau(rawReader.buildList());
				compresseur.compresseur(args[0].equals("-i"), false);
				SegWriter sw = new SegWriter(args[1].split(".")[0] + ".seg", rawReader.getData());
				sw.write(compresseur.getChemin());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
