package com.github.zhupan;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author PanosZhu
 */
public class JMeterLogMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] line = value.toString().split(",", 10);
        if (line.length != 10) {
            System.out.println("-" + line.length);
            return;
        }
        String outputKey = line[2];
        double outputValue = Double.parseDouble(line[1]);
        context.write(new Text(outputKey), new DoubleWritable(outputValue));
    }
}
