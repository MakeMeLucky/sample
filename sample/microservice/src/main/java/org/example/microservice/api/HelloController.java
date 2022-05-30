package org.example.microservice.api;

import lombok.Data;
import org.example.microservice.kafka.MyConsumer;
import org.example.microservice.kafka.MyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    MyConsumer consumer;

    @Autowired
    MyProducer producer;

    @RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/producer")
    @ResponseBody
    public String testProducer() {
        producer.send("my-topic-1","Test");
        return "Sent";
    }

    @RequestMapping(value = "/consumer")
    @ResponseBody
    public List<String> testConsumer() {
        return consumer.poll();
    }

    @Data
    static class Result {
        private final int left;
        private final int right;
        private final long answer;
    }

    // SQL sample
    @RequestMapping("calc")
    Result calc(@RequestParam int left, @RequestParam int right) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("left", left)
                .addValue("right", right);
        return jdbcTemplate.queryForObject("SELECT :left + :right AS answer", source,
                (rs, rowNum) -> new Result(left, right, rs.getLong("answer")));
    }
}
