package cn.ls.hbase;

import org.apache.commons.collections.KeyValue;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapOutputCollector;

import java.io.IOException;

/**
 * Created by Administrator on 2016/9/10.
 */
public class generateHFile {
    public static class generateHFileMapper extends Mapper<LongWritable,
            Text, ImmutableBytesWritable, KeyValue> {
        @Override
        protected void map(LongWritable key, Text value, MapOutputCollector.Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] items = line.split(",", -1);
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(items[0].getBytes());
            KeyValue kvProtocol = new KeyValue(items[0].getBytes(), "colfam1".getBytes(),
                    "colfam1".getBytes(), items[0].getBytes());
            if (null != kvProtocol) {
                context.write(rowkey, kvProtocol);
            }
        }
    }

    public static void main(String[] args) throws IOException,
            InterruptedException, ClassNotFoundException {
        Configuration conf = HBaseConfiguration.create();
        System.out.println("conf=" + conf);
        HTable table = new HTable(conf, "testtable1");
        System.out.println("table=" + table);
        Job job = new Job(conf, "generateHFile");
        job.setJarByClass(generateHFile.class);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(KeyValue.class);
        job.setMapperClass(generateHFileMapper.class);
        job.setReducerClass(KeyValueSortReducer.class);
        job.setOutputFormatClass(HFileOutputFormat.class);//组织成 HFile 文件
//自动对 job 进行配置，SimpleTotalOrderPartitioner 是需要先对 key 进行整体排序，
//然后划分到每个 reduce 中，保证每一个 reducer 中的的 key 最小最大值区间范围，是不会有交集的。
        HFileOutputFormat.configureIncrementalLoad(job, table);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
