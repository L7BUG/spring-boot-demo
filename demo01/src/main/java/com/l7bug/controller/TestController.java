package com.l7bug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l7bug.config.LogAop;
import com.l7bug.config.SaveLog;
import com.l7bug.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * TestController
 *
 * @author Administrator
 * @since 2025/4/15 10:28
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {
    @SaveLog(content = "'测试记录' + #args[0] + '->' + #args[1]", business = SaveLog.Business.COORDINATE, id = "'123321'")
    @GetMapping
    public String test(@RequestParam("id") String id, @RequestParam("desc") String desc) {
        return id + ":" + desc;
    }

    @SaveLog(content = "'测试记录' + #args[0] + '->' + #args[1] + '临时数据->' + #temp", business = SaveLog.Business.COORDINATE, id = "'123321'", saveMethod = "@logService.saveLog(#logObj)")
    @GetMapping("/2")
    public String test2(@RequestParam("id") String id, @RequestParam("desc") String desc) {
        LogAop.putLogObject("temp", "tttttttttttttttttttttttttttttttttttttt");
        return id + ":" + desc;
    }

    @SaveLog(content = "@la.username() + '编辑了用户信息,修改:' + @la.diffFieldMsg(#oldData,#args[0])", business = SaveLog.Business.COORDINATE, id = "'123321'", saveMethod = "@logService.saveLog(#logObj)")
    @PostMapping("/3")
    public User test3(@RequestBody User user) throws Exception {
        String dbDataJson = """
                {
                  "name": "张三",
                  "city": "北京",
                  "phone": {
                    "number": "19999999999",
                    "type": "86"
                  }
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        User oldData = objectMapper.readValue(dbDataJson, User.class);
        LogAop.putLogObject("oldData", oldData);
        return user;
    }

}
