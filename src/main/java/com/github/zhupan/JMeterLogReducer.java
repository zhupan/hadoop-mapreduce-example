package com.github.zhupan;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;

/**
 * @author PanosZhu
 */
public class JMeterLogReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        double totalValue = 0;
        List<Double> list = new ArrayList<Double>();
        for (DoubleWritable value : values) {
            list.add(value.get());
            totalValue += value.get();
        }
        Double[] sortDoubles = SortUtils.sort(list.toArray(new Double[]{}));
        int samples = sortDoubles.length;
        context.write(new Text(key.toString() + " Total Samples"), new DoubleWritable(samples));
        context.write(new Text("Average Value"), new DoubleWritable(totalValue / samples));
        context.write(new Text("Median Value"), new DoubleWritable(sortDoubles[samples / 2]));
        context.write(new Text("90% Line Value"), new DoubleWritable(sortDoubles[Double.valueOf(samples * 0.9).intValue()]));
        context.write(new Text("Min Value"), new DoubleWritable(sortDoubles[0]));
        context.write(new Text("Max Value"), new DoubleWritable(sortDoubles[samples - 1]));
    }

}
