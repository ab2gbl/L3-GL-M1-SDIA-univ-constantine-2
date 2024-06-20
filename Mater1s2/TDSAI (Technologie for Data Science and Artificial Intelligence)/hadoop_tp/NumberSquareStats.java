import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NumberSquareStats {

    public static class SquareMapper
       extends Mapper<Object, Text, Text, IntWritable>{

        private Text category = new Text();
        private IntWritable numberSquare = new IntWritable();

        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
            int num = Integer.parseInt(value.toString());
            int square = num * num;
            if (num % 2 == 0) {
                category.set("pair");
                context.write(category, new IntWritable(square));
            } else {
                category.set("impair");
                context.write(category, new IntWritable(square));
            }
        }
    }

    public static class SumReducer
       extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
                           ) throws IOException, InterruptedException {
            if (key.toString().equals("pair")) {
                int sum = 0;
                for (IntWritable val : values) {
                    sum += val.get();
                }
                result.set(sum);
                context.write(key, result);
            } else {
                int min = Integer.MAX_VALUE;
                for (IntWritable val : values) {
                    min = Math.min(min, val.get());
                }
                result.set(min);
                context.write(key, result);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "number square stats");
        job.setJarByClass(NumberSquareStats.class);
        job.setMapperClass(SquareMapper.class);
        job.setReducerClass(SumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
