package com.cds.bigdata;

import java.io.File;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * <p></p>
 * author chendongsheng5 2017/4/13 13:47
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/13 13:47
 * modify by reason:{方法名}:{原因}
 */
public class ShortOptions {

  private static void options() {

    OptionParser parser = new OptionParser("aB?*.");
//    OptionParser parser = new OptionParser();

    OptionSet options = parser.parse("-a", "-B", "-?");

    System.out.println(options.has("a"));
    System.out.println(options.has("B"));
    System.out.println(options.has("?"));
    System.out.println(options.has("*"));
    System.out.println(options.has("."));
  }

  private static void argumentsOfOptions() {

    OptionParser parser = new OptionParser("fc:q::");
//    OptionParser parser = new OptionParser();

    OptionSet options = parser.parse("-f", "-c", "foo", "-q");

    System.out.println(options.has("f"));
    System.out.println(options.has("c"));
    System.out.println(options.hasArgument("c"));
    System.out.println(options.valueOf("c"));
    System.out.println(options.has("q"));
    System.out.println(options.hasArgument("q"));
    System.out.println(options.valueOf("q"));
  }

  private static void specifyingArguments() {
    OptionParser parser = new OptionParser("a:b:c::");

    OptionSet options = parser.parse("-a", "foo", "-bbar", "-c=baz");
    System.out.println(options.has("a"));
    System.out.println(options.valueOf("a"));

    System.out.println(options.valueOf("b"));
  }

  private static void acceptArguments() {
    OptionParser parser = new OptionParser();
    parser.accepts("a");
    parser.accepts("B");
    parser.accepts("c").withRequiredArg();

    OptionSet options = parser.parse("-aBcfoo");
    System.out.println(options.has("a"));
    System.out.println(options.valueOf("c"));
  }

  private static void optionSpec() {
    OptionParser parser = new OptionParser();
    OptionSpec<Integer> count = parser.accepts("count").withRequiredArg().ofType(Integer.class);
    OptionSpec<File> outputDir = parser.accepts("output-dir").withOptionalArg().ofType(File.class);
    OptionSpec<Void> verbose = parser.accepts("verbose");
    OptionSpec<File> files = parser.nonOptions().ofType(File.class);

    OptionSet options = parser
        .parse("--count", "3", "--output-dir", "/tmp", "--verbose", "a.txt", "b.txt");

    System.out.println(options.has(count));
    System.out.println(options.hasArgument(count));
    System.out.println(options.valueOf(count));
    System.out.println(count.values(options));
  }

  public static void main(String[] args) {
//    options();
//    argumentsOfOptions();
//    specifyingArguments();
//    acceptArguments();
    optionSpec();
  }

}
