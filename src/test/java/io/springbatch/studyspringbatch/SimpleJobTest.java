package io.springbatch.studyspringbatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {SimpleJobConfiguration.class, TestBatchConfig.class})
public class SimpleJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void simpleJobTest() throws Exception {
        // given
        JobParameters jobParameter = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("date", new Date().getTime())
                .toJobParameters();

        // when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameter);
        JobExecution jobExecution1 = jobLauncherTestUtils.launchStep("step1");

        // then
//        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
//        Assert.assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);

        StepExecution stepExecution = ((List<StepExecution>) jobExecution1.getStepExecutions()).get(0);

        Assert.assertEquals(stepExecution.getCommitCount(), 11);
        Assert.assertEquals(stepExecution.getReadCount(), 1000);
        Assert.assertEquals(stepExecution.getWriteCount(), 1000);
    }

    @After
    public void clear() {
        jdbcTemplate.execute("delete from customer2");
    }
}