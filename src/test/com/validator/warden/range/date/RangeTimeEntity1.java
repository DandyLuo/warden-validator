package com.validator.warden.range.date;

import java.util.Date;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.annotation.Matchers;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class RangeTimeEntity1 {

    @Matchers({
            @Matcher(group = "test1", range = "['2019-07-13 12:00:23.321', '2019-07-23 12:00:23.321']"),
            @Matcher(group = "test2", range = "(now, '2199-08-23 12:00:23.321']"),
            @Matcher(group = "test3", range = "past"),
            @Matcher(group = "test4", range = "future"),
    })
    private Date date;

    @Matchers({
            @Matcher(group = "test1", range = "['2019-08-03 12:00:23.321', '2019-08-13 12:00:23.321']"),
            @Matcher(group = "test2", range = "(now, '2199-08-13 12:00:23.321']"),
            @Matcher(group = "test3", range = "past"),
            @Matcher(group = "test4", range = "future"),
            @Matcher(group = "test5", range = "[100, 30000]"),
            @Matcher(group = "test6", range = "('2019-07-13 12:00:23.321', now]"),
            @Matcher(group = "test7", range = "(null, now)"),
            @Matcher(group = "test8", range = "('null', 'now')"),
            @Matcher(group = "test9", range = "['2019-08', '2019-08-13 12:00:23.321']"),
    })
    private Long time;

    @Matchers({
            @Matcher(group = "test1", range = "[100, 200]"),
    })
    private Long length;
}
