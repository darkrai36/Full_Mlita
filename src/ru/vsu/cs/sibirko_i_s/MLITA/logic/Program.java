package ru.vsu.cs.sibirko_i_s.MLITA.logic;

import org.apache.commons.cli.*;
import ru.vsu.cs.sibirko_i_s.MLITA.gui.Menu;
import ru.vsu.cs.sibirko_i_s.MLITA.util.ArrayUtils;
import ru.vsu.cs.sibirko_i_s.MLITA.util.SwingUtils;

import java.io.PrintStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
    public static String defaultFileDirectory = "./Tests/";

    public static void main(String[] args) {
//        try {
//            Locale.setDefault(Locale.ROOT);
//
//            Options options = defaultOptions();
//
//            CmdParams params = parseArgs(options, args);
//
//            if (params.help) {
//                String usage = "[-w] or [-i <input-file> (-o <output-file>)]";
//                new HelpFormatter().printHelp(usage, options);
//                System.exit(0);
//            }
//
//            if (params.window) {
//                winMain();
//            } else {
//                throw new Exception("No valid options used. Try \"--help\" for more information.");
//            }
//        } catch (Exception exception) {
//            System.err.println(exception.getMessage());
//            System.exit(1);
//        }
        winMain();
    }

    public static void winMain() {
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    public static class CmdParams {
        public boolean help;
        public boolean window;
    }

    public static CmdParams parseArgs(Options options, String[] args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        CmdParams params = new CmdParams();
        if (cmd.hasOption("help")) {
            params.help = true;
            return params;
        }
        if (cmd.hasOption("window")) {
            params.window = true;
            return params;
        }
        return params;
    }

    public static Options defaultOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "print list of options");
        options.addOption("w", "window", false, "launches GUI");
        return options;
    }

    public static String fileNameValidation(String fileName) throws Exception {
        fileName = fileName == null ? "" : fileName;

        String extension = "txt";
        String validName;

        Matcher matcher = Pattern.compile("(\\p{Alnum}+).?(\\p{Alpha}*)").matcher(fileName);
        if (matcher.matches()) {
            if (!matcher.group(2).equals(extension)) {
                validName = matcher.group(1) + "." + extension;
            } else {
                validName = fileName;
            }
        } else {
            throw new Exception("Invalid file name");
        }
        return validName;
    }
}
