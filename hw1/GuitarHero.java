public class GuitarHero {

    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static int numKeys = keyboard.length();
    private static double[] frequencies() {
        double[] freq = new double[numKeys];
        for (int i = 0; i < numKeys; i++) {
            freq[i] = 440 * Math.pow(2.0, (i - 24) / 12.0);
        }
        return freq;
    }

    public static void main(String[] args) {
        synthesizer.GuitarString[] strings = new synthesizer.GuitarString[numKeys];
        for (int i = 0; i < numKeys; i++) {
            strings[i] = new synthesizer.GuitarString(frequencies()[i]);
        }


        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) != -1) {
                    int idx = keyboard.indexOf(key);
                    strings[idx].pluck();
                }

            }

            /* compute the superposition of samples */
            double sample = 0;
            for (synthesizer.GuitarString string : strings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (synthesizer.GuitarString string : strings) {
                string.tic();
            }
        }
    }
}
