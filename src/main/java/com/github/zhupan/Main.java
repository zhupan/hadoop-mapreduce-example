package com.github.zhupan;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author PanosZhu
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            args = new String[]{"/Users/zhupan/github/hadoop-mapreduce-example/input", "/Users/zhupan/github/hadoop-mapreduce-example/output"};
//            System.err.println("Usage: hadoopex <input path> <output path>");
//            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(Main.class);
        job.setJobName("JMeter Log");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(JMeterLogMapper.class);
        job.setReducerClass(JMeterLogReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
