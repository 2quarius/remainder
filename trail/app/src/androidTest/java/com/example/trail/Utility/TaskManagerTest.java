package com.example.trail.Utility;

import com.example.trail.NewTask.SimpleTask.Priority;
import com.example.trail.NewTask.SimpleTask.Task;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;

public class TaskManagerTest {
    private List<Task> tasks = new ArrayList<>();
    private TaskManager manager;
    @Before
    public void initTask()
    {
        List<Priority> priorities = new ArrayList<>();
        priorities.add(Priority.EUGEN);
        priorities.add(Priority.MIDDLE);
        priorities.add(Priority.LITTLE);
        priorities.add(Priority.NONE);
        Random random = new Random();
        //生成有优先级的task
        for (int i = 0;i < 10;i++)
        {
            //生成有起止时间的task
            Task task = new Task();
            task.setPriority(priorities.get(random.nextInt(priorities.size())));
            task.setStartTime(randomDate("2019-7-19","2019-7-29"));
            task.setExpireTime(randomDate("2019-8-1","2019-8-3"));
            tasks.add(task);
        }
        for (int i = 0;i < 10;i++)
        {
            //生成只有ddl的task
            Task task = new Task();
            task.setPriority(priorities.get(random.nextInt(priorities.size())));
            task.setExpireTime(randomDate("2019-7-19","2019-8-3"));
            tasks.add(task);
        }
        //生成没有优先级的task
        for (int i = 0;i < 5;i++)
        {
            //生成只有ddl的task
            Task task = new Task();
            task.setPriority(priorities.get(random.nextInt(priorities.size())));
            task.setExpireTime(randomDate("2019-7-19","2019-8-3"));
            tasks.add(task);
        }
        manager = new TaskManager();
    }
    @Test
    public void testManagement(){
        assertThat(manager.getManagedTasks(tasks).size(), allOf(greaterThan(-1),lessThan(26)));
    }
    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

}