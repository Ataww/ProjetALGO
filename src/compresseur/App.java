package compresseur;

import java.io.File;
import java.io.IOException;
import java.util.List;

import reader.RawReader;
import util.ImgMultiplier;
import util.RawWriter;
import util.SegReader;
import util.SegWriter;

public class App {

	public App() {

	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("not enough arguments, expecting mode and image's path");
		} else if (!(args[0].equals("-r") || args[0].equals("-i") || args[0].equals("-g") || args[0].equals("-d"))) {
			throw new IllegalArgumentException("invalid mode, -i, -r, -d or -g expected");
		}
		if (!new File(args[1]).exists()) {
			throw new IllegalArgumentException("invalid image path, does not exists");
		}
		if (args[0].equals("-g")) { // agrandir l'image
			try {
				RawReader reader = new RawReader(args[1]);
				reader.read(false);
				ImgMultiplier mult = new ImgMultiplier(reader.getData());
				byte[] output = mult.grow();
				String[] fileSegments = args[1].split("\\.");
				StringBuilder sb = new StringBuilder(fileSegments[0]);
				for (int i = 1; i < fileSegments.length - 2; i++) {
					sb.append("." + fileSegments[i]);
				}
				sb.append("_g.");
				sb.append(fileSegments[fileSegments.length - 1]);
				RawWriter writer = new RawWriter(sb.toString());
				writer.write(output);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(args[0].equals("-d")){ // décompresser l'image
			String[] fileSegments = args[1].split("\\.");
			if(!fileSegments[fileSegments.length-1].equals("seg")) {
				throw new IllegalArgumentException("seg file expected for decompression");
			}
			StringBuilder sb = new StringBuilder(fileSegments[0]);
			for(int i = 1; i < fileSegments.length - 2; i++) {
				sb.append("."+fileSegments[i]);
			}
			sb.append(".raw");
			SegReader sr = new SegReader(args[1]);
			List<Pixel> pxls = sr.read();
			RawWriter rw = new RawWriter(sb.toString());
			rw.write(pxls);
		}else { // compression
			try {
				RawReader rawReader = new RawReader(args[1]);
				rawReader.read(true);
				Rouleau compresseur = new Rouleau(rawReader.buildList());
				compresseur.compresseur(args[0].equals("-i"), false);
				String[] fileSegments = args[1].split("\\.");
				StringBuilder sb = new StringBuilder(fileSegments[0]);
				for(int i = 1; i < fileSegments.length - 2; i++) {
					sb.append("."+fileSegments[i]);
				}
				sb.append(".seg");
				SegWriter sw = new SegWriter(sb.toString(), rawReader.getData());
				sw.write(compresseur.getChemin());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
